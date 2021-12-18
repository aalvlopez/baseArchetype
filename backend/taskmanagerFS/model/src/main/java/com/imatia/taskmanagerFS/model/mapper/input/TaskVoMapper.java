package com.imatia.taskmanagerFS.model.mapper.input;

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
public abstract class TaskVoMapper {

    public abstract TaskVO fromTaskDto(TaskDto task);

    protected UserVO mapStringToUserVo(String owner) {
        return UserVO.builder().username(owner).build();
    }
}
