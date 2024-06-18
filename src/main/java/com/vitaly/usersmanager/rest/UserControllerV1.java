package com.vitaly.usersmanager.rest;


import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.mapper.UserMapper;
import com.vitaly.usersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UserService userService;

    private final UserMapper userMapper;

    @RequestMapping
    public String getAllUsers() {
        return "Hello World";
    }

//    @PostMapping("/test")
//    public Mono<ResponseEntity<TestUserDto>> getUser(@RequestBody TestUserDto userDto){
//       log.info("userDto = {}", userDto);
//
//        return userService.save(userMapper.toUserEntity(userDto)).
//               map(savedUser -> ResponseEntity.ok(userMapper.toUserDto(savedUser)));
//    }

    @GetMapping("/test/{id}")
    public Mono<ResponseEntity<UserEntity>> getUserById(@PathVariable("id") UUID id) {
     //   return userService.getById(id).map(userMapper::toUserDto).map(ResponseEntity::ok);
        return userService.getById(id)
                .doOnNext(entity -> log.warn("Entity from repository: {}", entity))
                .map(ResponseEntity::ok);
    }
}
