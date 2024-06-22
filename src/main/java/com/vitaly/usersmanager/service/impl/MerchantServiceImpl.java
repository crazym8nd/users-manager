package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.MerchantEntity;
import com.vitaly.usersmanager.repository.MerchantRepository;
import com.vitaly.usersmanager.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    @Override
    public Mono<MerchantEntity> getById(UUID uuid) {
        return merchantRepository.findById(uuid);
    }

    @Override
    public Mono<MerchantEntity> update(MerchantEntity merchantEntity) {
        return merchantRepository.save(merchantEntity);
    }

    @Override
    public Mono<MerchantEntity> save(MerchantEntity merchantEntity) {
        return merchantRepository.save(merchantEntity);
    }

    @Override
    public Mono<MerchantEntity> delete(UUID uuid) {
        return merchantRepository.findById(uuid)
                .flatMap((merchant -> merchantRepository.deleteById(merchant.getId())
                        .thenReturn(merchant)));
    }
}
