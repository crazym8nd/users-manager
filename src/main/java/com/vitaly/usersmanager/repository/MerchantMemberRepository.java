package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.MerchantMemberEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MerchantMemberRepository extends R2dbcRepository<MerchantMemberEntity, UUID> {
    @Modifying
    @Query("UPDATE person.merchant_members SET status = 'DELETED' WHERE id = :id")
    Mono<Void> deleteById(UUID id);
}
