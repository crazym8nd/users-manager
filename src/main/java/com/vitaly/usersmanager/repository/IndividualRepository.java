package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.IndividualEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IndividualRepository extends R2dbcRepository<IndividualEntity, UUID> {
}
