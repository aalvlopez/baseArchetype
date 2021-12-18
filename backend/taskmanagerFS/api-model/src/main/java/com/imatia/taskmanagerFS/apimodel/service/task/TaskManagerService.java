package com.imatia.taskmanagerFS.apimodel.service.task;

import java.util.List;

import com.imatia.dto.TaskDto;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
public interface TaskManagerService {

    TaskDto createTask(TaskDto task);

    void deleteTask(Integer taskId);

    TaskDto getTask(Integer taskId);

    List<TaskDto> getTasks(Integer page, Integer size);
}
