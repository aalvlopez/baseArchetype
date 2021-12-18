package com.imatia.taskmanagerFS.model.mapper.output;

import com.imatia.dto.UserDto;
import com.imatia.taskmanagerFS.apimodel.entity.user.RoleVO;
import com.imatia.taskmanagerFS.apimodel.entity.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserDtoMapper {

    public abstract UserDto fromUserVo(UserVO user);

    protected String mapRoleVoToString(RoleVO role) {
        return role.getName();
    }
}
