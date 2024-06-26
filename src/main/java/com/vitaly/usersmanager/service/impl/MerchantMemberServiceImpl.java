package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.MerchantMemberEntity;
import com.vitaly.usersmanager.repository.MerchantMemberRepository;
import com.vitaly.usersmanager.service.MerchantMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantMemberServiceImpl implements MerchantMemberService {
    private final MerchantMemberRepository merchantMemberRepository;

    @Override
    public Mono<MerchantMemberEntity> getById(UUID uuid) {
        return merchantMemberRepository.findById(uuid);
    }

    @Override
    public Mono<MerchantMemberEntity> update(MerchantMemberEntity merchantMemberEntity) {
        return merchantMemberRepository.save(merchantMemberEntity.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public Mono<MerchantMemberEntity> save(MerchantMemberEntity merchantMemberEntity) {
        return merchantMemberRepository.save(merchantMemberEntity);
    }

    @Override
    public Mono<MerchantMemberEntity> delete(UUID uuid) {
        return merchantMemberRepository.findById(uuid)
                .flatMap((merchantMember -> merchantMemberRepository.deleteById(merchantMember.getId())
                        .thenReturn(merchantMember)));
    }
}
