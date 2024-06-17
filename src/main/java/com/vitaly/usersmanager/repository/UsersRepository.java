package com.vitaly.usersmanager.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends R2dbcRepository<UserEntity, UUID> {
}
