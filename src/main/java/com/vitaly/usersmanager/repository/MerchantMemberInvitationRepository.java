package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.MerchantMemberInvitationEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantMemberInvitationRepository extends R2dbcRepository<MerchantMemberInvitationEntity, UUID> {
}
