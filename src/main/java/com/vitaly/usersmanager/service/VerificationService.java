package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.IndividualEntity;
import reactor.core.publisher.Mono;

public interface VerificationService {
    void jobForVerifying();

    Mono<Void> verify(IndividualEntity individual);
}
