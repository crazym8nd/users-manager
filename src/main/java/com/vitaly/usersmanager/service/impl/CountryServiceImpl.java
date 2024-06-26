package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.CountryEntity;
import com.vitaly.usersmanager.repository.CountryRepository;
import com.vitaly.usersmanager.service.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Mono<CountryEntity> getById(Long aLong) {
        return countryRepository.findById(aLong);
    }

    @Override
    public Mono<CountryEntity> update(CountryEntity countryEntity) {
        return countryRepository.save(countryEntity);
    }

    @Override
    public Mono<CountryEntity> save(CountryEntity countryEntity) {
        return countryRepository.save(countryEntity);
    }

    @Override
    public Mono<CountryEntity> delete(Long aLong) {
        return countryRepository.findById(aLong)
                .flatMap((country -> countryRepository.deleteById(country.getId()).thenReturn(country)));
    }

    @Override
    public Mono<CountryEntity> findByName(String name) {
        return countryRepository.findByName(name);
    }
}
