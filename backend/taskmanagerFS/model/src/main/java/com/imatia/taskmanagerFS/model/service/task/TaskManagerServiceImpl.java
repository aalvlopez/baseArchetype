package com.imatia.taskmanagerFS.model.service.task;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.imatia.dto.TaskDto;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;
import com.imatia.taskmanagerFS.apimodel.service.task.TaskManagerService;
import com.imatia.taskmanagerFS.model.mapper.input.TaskVoMapper;
import com.imatia.taskmanagerFS.model.mapper.output.TaskDtoMapper;
import com.imatia.taskmanagerFS.model.repository.task.TaskRepository;
import com.imatia.taskmanagerFS.model.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskManagerServiceImpl.class);

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
        Assert.isTrue(this.userRepository.findByUsername(task.getOwner()).isPresent(), "The user does not exist");

        final TaskVO taskVO = this.taskVOMapper.fromTaskDto(task);
        final TaskVO savedTask = this.taskRepository.save(taskVO);

        return this.taskDtoMapper.fromTaskDto(savedTask);
    }

    @Override
    public void deleteTask(Integer taskId) {
        Assert.notNull(taskId, "The id must not be null");
        final Optional<TaskVO> taskOpt = this.taskRepository.findById(taskId);
        Assert.isTrue(taskOpt.isPresent(), "The task does not exist");

        this.taskRepository.delete(taskOpt.get());
    }

    @Override
    public TaskDto getTask(Integer taskId) {
        Assert.notNull(taskId, "The id must not be null");
        final Optional<TaskVO> taskOpt = this.taskRepository.findById(taskId);
        Assert.isTrue(taskOpt.isPresent(), "The task does not exist");

        return this.taskDtoMapper.fromTaskDto(taskOpt.get());
    }


}
