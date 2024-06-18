package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.repository.UserRepository;
import com.vitaly.usersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<UserEntity> getById(UUID uuid) {
        return userRepository.findById(uuid)
                .doOnNext(entity -> log.warn("Entity from repository: {}", entity))
                .map(user -> user);
    }

    @Override
    public Mono<UserEntity> update(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public Mono<UserEntity> delete(UUID uuid) {
        return userRepository.findById(uuid)
                .flatMap(( user -> userRepository.deleteById(user.getId()).thenReturn(user)));
    }
}
