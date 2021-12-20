package com.imatia.taskmanagerFS.model.service.task;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.imatia.dto.TaskDto;
import com.imatia.dto.TaskListDto;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskStatusEnum;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;
import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;
import com.imatia.taskmanagerFS.apimodel.exception.EntityNotFoundException;
import com.imatia.taskmanagerFS.apimodel.service.task.TaskManagerService;
import com.imatia.taskmanagerFS.model.mapper.input.TaskVoMapper;
import com.imatia.taskmanagerFS.model.mapper.output.TaskDtoMapper;
import com.imatia.taskmanagerFS.model.repository.task.TaskRepository;
import com.imatia.taskmanagerFS.model.repository.user.UserRepository;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskVoMapper taskVOMapper;

    @Autowired
    private TaskDtoMapper taskDtoMapper;

    @Override
    public TaskDto createTask(TaskDto task) {
        Assert.notNull(task, "The task must not be null");
        this.getUser(task.getOwner());

        return this.taskDtoMapper.fromTaskDto(this.taskRepository.save(this.taskVOMapper.fromTaskDto(task)));
    }

    @Override
    public void deleteTask(Integer taskId) {
        this.taskRepository.delete(this.getTaskVO(taskId));
    }

    @Override
    public TaskDto getTask(Integer taskId) {
        return this.taskDtoMapper.fromTaskDto(getTaskVO(taskId));
    }

    public TaskListDto getTasks(Integer page, Integer size) {
        Assert.notNull(page, "The limit must not be null");
        Assert.notNull(size, "The offset must not be null");

        final Page<TaskVO> all = this.taskRepository.findAll(PageRequest.of(page, size).withSort(Sort.by(
            Sort.Order.desc("creationDateTime"),
            Sort.Order.desc("id"))));
        TaskListDto result = new TaskListDto();

        result.setTasks( this.taskDtoMapper.fromTaskDto(all.get().collect(Collectors.toList())));
        result.setCount(this.taskRepository.countTasks());

        return result;
    }

    @Override
    public void completeTask(Integer taskId) {
        final TaskVO taskVO = getTaskVO(taskId);
        taskVO.setStatus(TaskStatusEnum.COMPLETE);
        this.taskRepository.save(taskVO);
    }

    private TaskVO getTaskVO(Integer taskId) {
        Assert.notNull(taskId, "The id must not be null");
        final Optional<TaskVO> taskOpt = this.taskRepository.findById(taskId);
        return taskOpt.orElseThrow(() -> new EntityNotFoundException("The task does not exist"));
    }

    private UserVO getUser(String username) {
        Assert.notNull(username, "The username must not be null");
        return this.userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("The user does not exist"));
    }

}
