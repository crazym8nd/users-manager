package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.AddressEntity;
import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.repository.AddressRepository;
import com.vitaly.usersmanager.repository.CountryRepository;
import com.vitaly.usersmanager.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;

    @Override
    public Mono<AddressEntity> getById(UUID uuid) {
        return addressRepository.findById(uuid);
    }

    @Override
    public Mono<AddressEntity> update(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity);
    }

    @Override
    public Mono<AddressEntity> save(AddressEntity addressEntity) {
        return addressRepository.save(addressEntity.toBuilder()
                        .status(EntityStatus.ACTIVE)
                .build());
    }

    @Override
    public Mono<AddressEntity> delete(UUID uuid) {
        return addressRepository.findById(uuid)
                .flatMap((address -> addressRepository.deleteById(address.getId()).thenReturn(address)));
    }

    @Override
    public Mono<AddressEntity> getByIdWithCountry(UUID uuid) {
        return addressRepository.findById(uuid)
                .flatMap(addressEntity -> countryRepository.findById(addressEntity.getCountryId())
                        .map(country -> {
                            addressEntity.setCountry(country);
                            return addressEntity;
                        }));

    }
}
