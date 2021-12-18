package com.imatia.taskmanagerFS.model.service.task;

import java.util.Optional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.imatia.dto.TaskDto;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;
import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;
import com.imatia.taskmanagerFS.model.mapper.input.TaskVoMapper;
import com.imatia.taskmanagerFS.model.mapper.output.TaskDtoMapper;
import com.imatia.taskmanagerFS.model.repository.task.TaskRepository;
import com.imatia.taskmanagerFS.model.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */

@Configuration
@SpringBootTest(classes = TaskManagerServiceImpl.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
class TaskManagerServiceTest {

    @MockBean
    TaskVoMapper taskVOMapper;

    @MockBean
    TaskDtoMapper taskDtoMapper;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    UserRepository userRepository;

    @SpyBean
    TaskManagerServiceImpl taskManagerService;

    @Test
    void createTask() {
        final String description = "description";
        final String title = "title";
        final String owner = "owner";

        final UserVO ownerUser = UserVO.builder().username(owner).build();

        final TaskDto task = new TaskDto();
        task.setDescription(description);
        task.setTitle(title);
        task.setOwner(owner);

        final TaskVO expectedSavedTask = new TaskVO();
        expectedSavedTask.setTitle(title);
        expectedSavedTask.setDescription(description);
        expectedSavedTask.setOwner(ownerUser);

        Mockito.when(taskVOMapper.fromTaskDto(task)).thenReturn(expectedSavedTask);
        Mockito.when(taskDtoMapper.fromTaskDto(expectedSavedTask)).thenReturn(task);
        Mockito.when(taskRepository.save(expectedSavedTask)).thenReturn(expectedSavedTask);
        Mockito.when(userRepository.findByUsername(owner)).thenReturn(Optional.of(ownerUser));

        Assertions.assertEquals(task, this.taskManagerService.createTask(task));
    }

    @Test
    void createTaskUserNotFound() {
        final String owner = "owner";

        final TaskDto task = new TaskDto();
        task.setOwner(owner);

        Mockito.when(userRepository.findByUsername(owner)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.taskManagerService.createTask(task));
    }

    @Test
    void deleteTask() {
        final int taskId = 1;
        final TaskVO task = TaskVO.builder().id(taskId).build();
        Mockito.when(this.taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Mockito.doNothing().when(this.taskRepository).delete(Mockito.any());
        this.taskManagerService.deleteTask(taskId);

        Mockito.verify(this.taskRepository, Mockito.times(taskId)).delete(task);
    }

    @Test
    void deleteTaskNullTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.taskManagerService.deleteTask(null));
    }

    @Test
    void deleteTaskTaskNotFound() {
        final int taskId = 1;
        Mockito.when(this.taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.taskManagerService.deleteTask(taskId));
    }

    @Test
    void getTask(){

        final int taskId = 1;
        final String description = "description";
        final String title = "title";
        final String owner = "owner";

        final UserVO ownerUser = UserVO.builder().username(owner).build();

        final TaskVO task = new TaskVO();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setOwner(ownerUser);

        final TaskDto expectedTask = new TaskDto();
        expectedTask.setDescription(description);
        expectedTask.setTitle(title);
        expectedTask.setOwner(owner);

        Mockito.when(taskDtoMapper.fromTaskDto(task)).thenReturn(expectedTask);
        Mockito.when(this.taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Assertions.assertEquals(expectedTask, this.taskManagerService.getTask(taskId));
    }

    @Test
    void getTaskNullTaskId() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.taskManagerService.getTask(null));
    }

    @Test
    void getTaskTaskNotFound() {
        final int taskId = 1;
        Mockito.when(this.taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.taskManagerService.getTask(taskId));
    }
}
