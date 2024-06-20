package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.IndividualEntity;
import com.vitaly.usersmanager.exceptionhandling.NotFoundException;
import com.vitaly.usersmanager.repository.IndividualRepository;
import com.vitaly.usersmanager.util.IndividualServiceUtil;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class IndividualServiceImplTest {

    private final IndividualRepository individualRepository = mock(IndividualRepository.class);
    private final IndividualServiceImpl individualService = new IndividualServiceImpl(individualRepository);


    @Test
    @Name("Should return individual")
    void givenValidUuid_whenGetById_thenReturnIndividual() {
        //given
        when(individualRepository.findById(UUID.fromString("79e7a181-37f1-44a6-b323-54edb52634b7"))).thenReturn(IndividualServiceUtil.getIndividualEntityForGetByIdTest());
        //when
        Mono<IndividualEntity> result = individualService.getById(UUID.fromString("79e7a181-37f1-44a6-b323-54edb52634b7"));

        // then
        StepVerifier.create(result)
                .expectNextMatches(
                        entity -> entity != null
                                && entity.getId().equals(UUID.fromString("79e7a181-37f1-44a6-b323-54edb52634b7")))
                .verifyComplete();
    }

    @Test
    @Name("Should return not found exception")
    void givenInvalidUuid_whenGetById_thenReturnNotFoundException() {
        //given
        when(individualRepository.findById(any(UUID.class))).thenReturn(Mono.empty());
        //when
        Mono<IndividualEntity> result = individualService.getById(UUID.randomUUID());

        // then
        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();
    }
}