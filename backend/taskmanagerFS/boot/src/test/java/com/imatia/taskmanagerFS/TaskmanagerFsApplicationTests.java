package com.imatia.taskmanagerFS;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.imatia.dto.TaskDto;
import com.imatia.dto.TaskDto.StatusEnum;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskStatusEnum;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;
import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;
import com.imatia.taskmanagerFS.model.repository.task.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TaskmanagerFsApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(TaskmanagerFsApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    static ObjectMapper objectMapper;

    @BeforeAll
    static void createObjectMapper() {
        final ObjectMapper newObjectMapper = new ObjectMapper();
        newObjectMapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
            @Override
            public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(offsetDateTime));
            }
        });
        TaskmanagerFsApplicationTests.objectMapper = newObjectMapper.registerModule(simpleModule);
    }

    List<TaskVO> createExampleTasks(int number) {
        List<TaskVO> insertedTasks = new ArrayList<>();

        final String title = "test title";
        final String description = "test description";
        final String ownerUser = "user";

        for (int i = 0; i < number; i++) {
            insertedTasks.add(this.taskRepository.save(TaskVO.builder()
                .title(title)
                .description(description)
                .owner(UserVO.builder().username(ownerUser).build())
                .status(TaskStatusEnum.PENDING)
                .creationDateTime(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS).plusSeconds(i))
                .build()));
        }

        return insertedTasks;
    }

    TaskVO createExampleTask() {
        return createExampleTasks(1).get(0);
    }

    @Test
    @Rollback
    void createTask_200() throws Exception {
        final OffsetDateTime currentDateTime = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final String s = currentDateTime.toString();
        logger.info("----> {}", s);

        final String title = "test title";
        final String description = "test description";
        final String ownerUser = "user";
        String request = "{\"title\": \"" + title + "\", \"description\": \"" + description + "\", \"owner\": \"" + ownerUser + "\", \"creationDateTime\": \"" + s + "\"}";

        final ResultActions resultActions = this.mockMvc.perform(post("/api/v1/task")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").doesNotExist());

        final Iterable<TaskVO> all = this.taskRepository.findAll();
        final List<TaskVO> filteredResults = StreamSupport.stream(all.spliterator(), false)
            .filter(t -> t.getDescription().equals(description)
                && t.getTitle().equals(title)
                && t.getCreationDateTime().atZoneSameInstant(ZoneOffset.UTC).equals(currentDateTime.atZoneSameInstant(ZoneOffset.UTC))).collect(Collectors.toList());

        Assertions.assertFalse(CollectionUtils.isEmpty(filteredResults));
        Assertions.assertEquals(1, filteredResults.size());
    }

    @Test
    @Rollback
    void taskDetail_200() throws Exception {
        final TaskVO savedTask = createExampleTask();

        final String url = "/api/v1/task/{taskId}";

        final ResultActions result = this.mockMvc.perform(get(url, savedTask.getId())
            .servletPath(UriComponentsBuilder.fromUriString(url).build(savedTask.getId()).toString()));

        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty());

        String strResponse = result.andReturn().getResponse().getContentAsString();
        final TaskDto taskDto = objectMapper.readValue(strResponse, TaskDto.class);
        Assertions.assertEquals(savedTask.getTitle(), taskDto.getTitle());
        Assertions.assertEquals(savedTask.getDescription(), taskDto.getDescription());
        Assertions.assertEquals(savedTask.getOwner().getUsername(), taskDto.getOwner());
        Assertions.assertEquals(StatusEnum.valueOf(savedTask.getStatus().name()), taskDto.getStatus());

    }

    @Test
    @Rollback
    void taskDetail_404() throws Exception {
        final String url = "/api/v1/task/{taskId}";

        final ResultActions result = this.mockMvc.perform(get(url, 1)
            .servletPath(UriComponentsBuilder.fromUriString(url).build(1).toString()));

        result.andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @Rollback
    void deleteTask_204() throws Exception {
        final String url = "/api/v1/task/{taskId}";

        final TaskVO savedTask = createExampleTask();

        final ResultActions result = this.mockMvc.perform(delete(url, savedTask.getId())
            .servletPath(UriComponentsBuilder.fromUriString(url).build(savedTask.getId()).toString()));
        result.andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());

        Assertions.assertFalse(this.taskRepository.findById(savedTask.getId()).isPresent());
    }

    @Test
    @Rollback
    void deleteTask_404() throws Exception {
        final String url = "/api/v1/task/{taskId}";

        final ResultActions result = this.mockMvc.perform(delete(url, 1)
            .servletPath(UriComponentsBuilder.fromUriString(url).build(1).toString()));
        result.andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @Rollback
    void listTasks_200() throws Exception {
        List<TaskVO> insertedTasks = createExampleTasks(20);

        final Integer page = 1;
        final Integer pageSize = 3;
        final String url = "/api/v1/task";

        final List<TaskVO> expectedTasks = insertedTasks
            .stream()
            .sorted(Comparator.comparing(TaskVO::getCreationDateTime).reversed())
            .collect(Collectors.toList())
            .subList(page * pageSize, page * pageSize + pageSize);

        final ResultActions result = this.mockMvc.perform(get(url)
            .servletPath(url)
            .param("page", page.toString())
            .param("size", pageSize.toString()));

        result.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty());

        String strResponse = result.andReturn().getResponse().getContentAsString();

        final List<TaskDto> obtainedTaskDtos = Arrays.asList(objectMapper.readValue(strResponse, TaskDto[].class));

        Assertions.assertEquals(expectedTasks.size(), obtainedTaskDtos.size());
        final List<Integer> expectedIds = expectedTasks.stream().map(TaskVO::getId).collect(Collectors.toList());
        final List<Integer> obtainedIds = obtainedTaskDtos.stream().map(TaskDto::getId).collect(Collectors.toList());
        Assertions.assertTrue(expectedIds.containsAll(obtainedIds) && obtainedIds.containsAll(expectedIds));

    }

    @Test
    @Rollback
    void updateTaskStatus_204() throws Exception {
        final TaskVO exampleTask = createExampleTask();
        final String url = "/api/v1/task/{taskId}/status";

        final ResultActions result = this.mockMvc.perform(put(url, exampleTask.getId())
            .servletPath(UriComponentsBuilder.fromUriString(url).build(exampleTask.getId()).toString()));
        result.andDo(print())
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$").doesNotExist());

        final Optional<TaskVO> byId = this.taskRepository.findById(exampleTask.getId());
        Assertions.assertTrue(byId.isPresent());
        Assertions.assertEquals(TaskStatusEnum.COMPLETE, byId.get().getStatus());
    }

    @Test
    @Rollback
    void updateTaskStatus_404() throws Exception {
        final String url = "/api/v1/task/{taskId}/status";

        final ResultActions result = this.mockMvc.perform(put(url, 1)
            .servletPath(UriComponentsBuilder.fromUriString(url).build(1).toString()));
        result.andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$").isNotEmpty());
    }

}
