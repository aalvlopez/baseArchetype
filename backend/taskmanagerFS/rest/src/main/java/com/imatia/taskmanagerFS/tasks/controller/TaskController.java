package com.imatia.taskmanagerFS.tasks.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.imatia.dto.TaskDto;
import com.imatia.service.TaskApi;
import com.imatia.taskmanagerFS.apimodel.service.task.TaskManagerService;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@RestController
public class TaskController implements TaskApi {

    @Autowired
    private TaskManagerService taskManagerService;

    @Override
    public ResponseEntity<Void> createTask(@Valid TaskDto taskDto) {
        final TaskDto task = this.taskManagerService.createTask(taskDto);

        if (task != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTask(Integer taskId) {
        this.taskManagerService.deleteTask(taskId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<TaskDto> taskDetail(Integer taskId) {
        return new ResponseEntity<>(this.taskManagerService.getTask(taskId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TaskDto>> listTasks(@NotNull @Valid Integer page, @NotNull @Valid Integer size) {
        return new ResponseEntity<>(this.taskManagerService.getTasks(page, size), HttpStatus.OK);
    }
}
