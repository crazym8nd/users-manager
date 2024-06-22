package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.VerificationStatusEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationStatusRepository extends R2dbcRepository<VerificationStatusEntity, UUID> {
}
