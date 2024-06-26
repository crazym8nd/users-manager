package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.IndividualEntity;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface IndividualService extends GenericService<IndividualEntity, UUID> {
    Flux<IndividualEntity> getAllNotVerifiedIndividuals();
}
