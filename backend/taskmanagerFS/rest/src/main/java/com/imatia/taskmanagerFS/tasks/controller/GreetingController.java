package com.imatia.taskmanagerFS.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.imatia.service.GreetingApi;


@RestController
public class GreetingController implements GreetingApi {

    private static final String template = "Hello, %s!";

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> greeting(String name) {
        return new ResponseEntity<>(String.format(template, name), HttpStatus.OK);

    }
}
