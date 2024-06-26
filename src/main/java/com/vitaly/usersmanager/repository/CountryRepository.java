package com.vitaly.usersmanager.repository;

import com.vitaly.usersmanager.entity.CountryEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CountryRepository extends R2dbcRepository<CountryEntity, Long> {

    @Modifying
    @Query("UPDATE person.countries SET status = 'DELETED' WHERE id = :id")
    Mono<Void> deleteById(Long id);

    Mono<CountryEntity> findByName(String name);
}
