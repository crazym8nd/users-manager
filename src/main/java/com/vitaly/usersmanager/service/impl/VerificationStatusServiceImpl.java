package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.VerificationStatusEntity;
import com.vitaly.usersmanager.repository.VerificationStatusRepository;
import com.vitaly.usersmanager.service.VerificationStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationStatusServiceImpl implements VerificationStatusService {
    private final VerificationStatusRepository verificationStatusRepository;

    @Override
    public Mono<VerificationStatusEntity> getById(UUID uuid) {
        return verificationStatusRepository.findById(uuid);
    }

    @Override
    public Mono<VerificationStatusEntity> update(VerificationStatusEntity verificationStatusEntity) {
        return verificationStatusRepository.save(verificationStatusEntity.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public Mono<VerificationStatusEntity> save(VerificationStatusEntity verificationStatusEntity) {
        return verificationStatusRepository.save(verificationStatusEntity);
    }

    @Override
    public Mono<VerificationStatusEntity> delete(UUID uuid) {
        return verificationStatusRepository.findById(uuid)
                .flatMap((verificationStatus -> verificationStatusRepository.deleteById(verificationStatus.getId())
                        .thenReturn(verificationStatus)));
    }
}
