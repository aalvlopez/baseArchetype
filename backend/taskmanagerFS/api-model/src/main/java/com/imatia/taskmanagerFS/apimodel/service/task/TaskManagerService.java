package com.imatia.taskmanagerFS.apimodel.service.task;

import com.imatia.dto.TaskDto;
import com.imatia.dto.TaskListDto;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
public interface TaskManagerService {

    TaskDto createTask(TaskDto task);

    void deleteTask(Integer taskId);

    TaskDto getTask(Integer taskId);

    TaskListDto getTasks(Integer page, Integer size);

    void completeTask(Integer taskId);
}
