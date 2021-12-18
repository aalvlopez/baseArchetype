package com.imatia.taskmanagerFS.apimodel.service.task;

import com.imatia.dto.TaskDto;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
public interface TaskManagerService {

    TaskDto createTask(TaskDto task);

    void deleteTask(Integer taskId);
}
