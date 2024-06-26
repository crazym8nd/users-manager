package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.IndividualEntity;
import com.vitaly.usersmanager.service.IndividualService;
import com.vitaly.usersmanager.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationServiceImpl implements VerificationService {

    private final IndividualService individualService;

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void jobForVerifying() {
        verifyUsers(individualService.getAllNotVerifiedIndividuals())
                .doOnSuccess(v -> log.warn("Successfully verified individuals! "))
                .subscribe();
    }

    @Override
    public Mono<Void> verify(IndividualEntity individual) {
        if (individual.getVerifiedAt() == null) {
            individual.setVerifiedAt(LocalDateTime.now());
            log.info("Individual is verified: {}", individual);
            return individualService.update(individual).thenEmpty(Mono.empty());
        }
        return Mono.empty();
    }
    public Mono<Void> verifyUsers(Flux<IndividualEntity> individuals){
        return individuals.flatMapSequential(this::verify)
                .then();
    }
}
