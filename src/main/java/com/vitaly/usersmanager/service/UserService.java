package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.UserEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService extends GenericService<UserEntity, UUID> {
    Mono<Boolean> checkEmailForUnique(String email);
    Mono<Boolean> checkPhoneForUnique(String phoneNumber);
}
