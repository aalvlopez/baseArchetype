package com.imatia.taskmanagerFS.model.repository.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Repository
public interface TaskRepository extends PagingAndSortingRepository<TaskVO, Integer> {

    @Query("select count(1) from TaskVO")
    public Integer countTasks();

}
