package com.vitaly.usersmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersManagerApplication.class, args);
    }
    //TODO: how to convert jsonb type from postgres to java type?
    //TODO: implement operations logic for individual
    //TODO: move dto package to other project and import it as dependency
    //TODO: add service and integration tests for individual
}
