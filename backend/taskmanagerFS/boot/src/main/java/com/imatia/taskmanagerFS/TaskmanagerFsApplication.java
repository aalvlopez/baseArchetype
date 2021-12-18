package com.imatia.taskmanagerFS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class TaskmanagerFsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskmanagerFsApplication.class, args);
    }

}
