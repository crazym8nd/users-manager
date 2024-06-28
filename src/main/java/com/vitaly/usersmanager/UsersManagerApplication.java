package com.vitaly.usersmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UsersManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersManagerApplication.class, args);
    }
    //TODO: move dto package to other project and import it as dependency
    //TODO: add service and integration tests for individual
}
