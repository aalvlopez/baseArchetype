package com.imatia.taskmanagerFS.apimodel.service.auth;

import com.imatia.dto.UserDto;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
public interface AuthService {
    public UserDto performLogin(String authHeader);
}
