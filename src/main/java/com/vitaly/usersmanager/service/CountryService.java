package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.CountryEntity;
import reactor.core.publisher.Mono;

public interface CountryService extends GenericService<CountryEntity, Long> {
    Mono<CountryEntity> findByName(String name);
}
