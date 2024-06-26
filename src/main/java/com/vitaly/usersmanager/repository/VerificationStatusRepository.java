package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.VerificationStatusEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface VerificationStatusRepository extends R2dbcRepository<VerificationStatusEntity, UUID> {
    @Modifying
    @Query("UPDATE person.verification_statuses SET status = 'DELETED' WHERE id = :id")
    Mono<Void> deleteById(UUID id);
}
