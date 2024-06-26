package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.service.FillingService;
import com.vitaly.usersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class FillingServiceImpl implements FillingService {

    private final UserService userService;

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void jobForCheckingFilledInfo() {
        checkIsFilledUsers(userService.getAllNotFilledUsers())
                .doOnSuccess(v -> log.warn("Successfully filled users! "))
                .subscribe();
    }

    @Override
    public Mono<Void> checkIsFilled(UserEntity user) {
        if (isUserFilled(user)) {
            user.setFilled(true);
            log.info("User is filled: {}", user);
            return userService.update(user).thenEmpty(Mono.empty());
        }
        return Mono.empty();
    }

    public Mono<Void> checkIsFilledUsers(Flux<UserEntity> users){
        return users.flatMapSequential(this::checkIsFilled)
                .then();
    }
    private boolean isUserFilled(UserEntity user) {
        return user.getSecretKey() != null
                && user.getCreatedAt() != null
                && user.getFirstName() != null
                && user.getLastName() != null
                && user.getPhoneNumber() != null
                && user.getEmail() != null
                && user.getStatus() != EntityStatus.DELETED
                && user.getAddressId() != null;
    }
}
