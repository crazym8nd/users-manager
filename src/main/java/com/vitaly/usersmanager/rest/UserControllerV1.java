package com.vitaly.usersmanager.rest;


import com.vitaly.dto.UserDto;
import com.vitaly.usersmanager.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UsersRepository usersRepository;

    @RequestMapping
    public String getAllUsers() {
        return "Hello World";
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<UserDto>> getUser(){
        return Mono.just(ResponseEntity.ok(UserDto.builder()
                        .firstName("Vitaly")
                        .lastName("TEST")
                .build()));
    }
}
