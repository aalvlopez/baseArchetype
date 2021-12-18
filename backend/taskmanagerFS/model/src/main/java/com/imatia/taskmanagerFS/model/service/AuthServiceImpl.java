package com.imatia.taskmanagerFS.model.service;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.imatia.dto.UserDto;
import com.imatia.taskmanagerFS.apimodel.entity.UserVO;
import com.imatia.taskmanagerFS.apimodel.service.AuthService;
import com.imatia.taskmanagerFS.model.mapper.UserDtoMapper;
import com.imatia.taskmanagerFS.model.repository.UserRepository;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDtoMapper userDtoMapper;

    private String getUsernameFromAuthHeaderString(String authHeader) {
        Assert.isTrue(!ObjectUtils.isEmpty(authHeader), "the token must not be null");

        final String base64 = authHeader.replace("Basic ", "");
        final String decodedAuthHeader = new String(Base64.getDecoder().decode(base64));
        final String[] split = decodedAuthHeader.split(":");

        return split[0];
    }

    @Override
    public UserDto performLogin(String authHeader) {
        String username = this.getUsernameFromAuthHeaderString(authHeader);
        final Optional<UserVO> byUsername = this.userRepository.findByUsername(username);
        return byUsername.map(userVO -> this.userDtoMapper.fromUserVo(userVO)).orElse(null);
    }

}
