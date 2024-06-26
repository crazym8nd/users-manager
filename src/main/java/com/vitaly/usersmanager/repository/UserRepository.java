package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {
    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByPhoneNumber(String phoneNumber);
    @Modifying
    @Query("UPDATE person.users SET status = 'DELETED' WHERE id = :id")
    Mono<Void> deleteById(UUID id);
}
