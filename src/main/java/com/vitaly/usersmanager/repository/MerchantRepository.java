package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.MerchantEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MerchantRepository extends R2dbcRepository<MerchantEntity, UUID> {
    @Modifying
    @Query("UPDATE person.merchants SET status = 'DELETED' WHERE id = :id")
    Mono<Void> deleteById(UUID id);
}
