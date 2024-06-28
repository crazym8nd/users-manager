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
    //TODO: add service unit tests
}
