package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.AddressEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AddressService extends GenericService<AddressEntity, UUID> {
    Mono<AddressEntity> getByIdWithCountry(UUID uuid);

}
