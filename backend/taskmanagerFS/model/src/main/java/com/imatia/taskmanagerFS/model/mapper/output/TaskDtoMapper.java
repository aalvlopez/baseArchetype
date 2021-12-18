package com.imatia.taskmanagerFS.model.mapper.output;

import java.util.List;

import com.imatia.dto.TaskDto;
import com.imatia.taskmanagerFS.apimodel.entity.task.TaskVO;
import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class TaskDtoMapper {

    public abstract List<TaskDto> fromTaskDto(List<TaskVO> tasks);

    public abstract TaskDto fromTaskDto(TaskVO task);

    protected String mapUserVoToString(UserVO owner) {
        return owner.getUsername();
    }

}
