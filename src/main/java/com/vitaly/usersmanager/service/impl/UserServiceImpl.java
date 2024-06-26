package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.exceptionhandling.UserAlreadyExistsException;
import com.vitaly.usersmanager.repository.AddressRepository;
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
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<UserEntity> getById(UUID uuid) {
        return userRepository.findById(uuid)
                .doOnNext(entity -> log.warn("Entity from repository: {}", entity))
                .map(user -> user);
    }

  @Override
    public Mono<UserEntity> getByIdWithAddress(UUID uuid) {
        return userRepository.findById(uuid)
                .flatMap(userEntity -> addressRepository.findById(userEntity.getAddressId())
                        .map(addressEntity -> {
                            userEntity.setAddress(addressEntity);
                            return userEntity;
                        }));
    }


    @Override
    public Mono<UserEntity> update(UserEntity userEntity) {
        return userRepository.save(userEntity);
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
