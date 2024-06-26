package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.IndividualEntity;
import com.vitaly.usersmanager.exceptionhandling.NotFoundException;
import com.vitaly.usersmanager.repository.IndividualRepository;
import com.vitaly.usersmanager.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualServiceImpl implements IndividualService {
    private final IndividualRepository individualRepository;


    @Override
    public Mono<IndividualEntity> getById(UUID uuid) {
        return individualRepository.findById(uuid)
                .doOnNext(entity -> log.warn("Entity from repository: {}", entity))
                .map(individual -> individual)
                .switchIfEmpty(Mono.error(new NotFoundException("Individual with id: " + uuid + " not found")));
    }

    @Override
    public Mono<IndividualEntity> update(IndividualEntity individualEntity) {
        return individualRepository.save(individualEntity.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public Mono<IndividualEntity> save(IndividualEntity individualEntity) {
        return individualRepository.save(individualEntity.toBuilder()
                .status(EntityStatus.ACTIVE)
                .build());
    }

    @Override
    public Mono<IndividualEntity> delete(UUID uuid) {
        return individualRepository.findById(uuid)
                .flatMap((individual -> individualRepository.deleteById(individual.getId()).thenReturn(individual)));
    }
}
