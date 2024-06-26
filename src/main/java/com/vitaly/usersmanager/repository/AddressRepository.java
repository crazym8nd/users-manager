package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.AddressEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface AddressRepository extends R2dbcRepository<AddressEntity, UUID> {
    @Modifying
    @Query("UPDATE person.addresses SET status = 'DELETED' WHERE id = :id")
    Mono<Void> deleteById(UUID id);
}
