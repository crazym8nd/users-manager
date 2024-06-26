package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface FillingService {

    void jobForCheckingFilledInfo();
    Mono<Void> checkIsFilled(UserEntity user);
}
