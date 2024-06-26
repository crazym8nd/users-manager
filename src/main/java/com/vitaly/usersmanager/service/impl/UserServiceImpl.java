package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.exceptionhandling.NotFoundException;
import com.vitaly.usersmanager.exceptionhandling.UserAlreadyExistsException;
import com.vitaly.usersmanager.repository.UserRepository;
import com.vitaly.usersmanager.service.AddressService;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import com.vitaly.usersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AddressService addressService;
    private final UserRepository userRepository;
    private final UserActionsHistoryService userActionsHistoryService;

    @Override
    public Mono<UserEntity> getById(UUID uuid) {
        return userRepository.findById(uuid)
                .doOnNext(entity -> log.warn("Entity from repository: {}", entity))
                .map(user -> user);
    }

    @Override
    public Mono<UserEntity> getByIdWithAddress(UUID uuid) {
        return userRepository.findById(uuid)
                .flatMap(userEntity -> {
                    if (userEntity.getAddressId() != null) {
                        return addressService.getByIdWithCountry(userEntity.getAddressId())
                                .map(addressEntity -> {
                                    userEntity.setAddress(addressEntity);
                                    return userEntity;
                                })
                                .switchIfEmpty(Mono.error(new NotFoundException("Address not found")));
                    } else {
                        return Mono.just(userEntity);
                    }
                });
    }

    @Override
    public Flux<UserEntity> getAllNotFilledUsers() {
        return userRepository.findAllByStatus(EntityStatus.ACTIVE).filter(userEntity -> !userEntity.isFilled());
    }

    @Override
    public Mono<UserEntity> update(UserEntity userEntity) {
        return userRepository.save(userEntity.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        return checkEmailForUnique(userEntity.getEmail())
                .then(checkPhoneForUnique(userEntity.getPhoneNumber()))
                .then(userRepository.save(userEntity.toBuilder()
                                .status(EntityStatus.ACTIVE)
                        .build()));
    }

    @Override
    public Mono<UserEntity> delete(UUID uuid) {
        return userRepository.findById(uuid)
                .flatMap((user -> userRepository.deleteById(user.getId()).thenReturn(user)));
    }

    @Override
    public Mono<Boolean> checkEmailForUnique(String email) {
        return userRepository.existsByEmail(email)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException("You already registered. Please login."));
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    @Override
    public Mono<Boolean> checkPhoneForUnique(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException("You already registered. Please login."));
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
