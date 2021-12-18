package com.imatia.taskmanagerFS.model.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskVO, Integer> {

}
