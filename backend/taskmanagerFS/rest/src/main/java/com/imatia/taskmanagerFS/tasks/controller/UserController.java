package com.imatia.taskmanagerFS.tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.imatia.dto.UserDto;
import com.imatia.service.UserApi;
import com.imatia.taskmanagerFS.model.service.AuthServiceImpl;

/**
 * @author <a href="changeme@ext.inditex.com">aalvarez</a>
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController implements UserApi {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @Override
    public ResponseEntity<UserDto> login(String authorization) {
        return new ResponseEntity<>(this.authServiceImpl.performLogin(authorization), HttpStatus.OK);
    }
}
