package com.vitaly.usersmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsersManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersManagerApplication.class, args);
    }
    // merchants service odin dlya vsego ili ewe bydet member service? a controller?
    //TODO: implement operations logic for individual
    //TODO: add template for other services
    //TODO: move dto package to other project and import it as dependency
    //TODO: add service and integration tests for individual
}
