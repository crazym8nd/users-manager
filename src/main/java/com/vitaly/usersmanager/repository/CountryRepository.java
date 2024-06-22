package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.CountryEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends R2dbcRepository<CountryEntity, Long> {
}
