package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.MerchantMemberInvitationEntity;
import com.vitaly.usersmanager.repository.MerchantMemberInvitationRepository;
import com.vitaly.usersmanager.service.MerchantMemberInvitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MerchantMemberInvitationServiceImpl implements MerchantMemberInvitationService {

    private final MerchantMemberInvitationRepository merchantMemberInvitationRepository;

    @Override
    public Mono<MerchantMemberInvitationEntity> getById(UUID uuid) {
        return merchantMemberInvitationRepository.findById(uuid);
    }

    @Override
    public Mono<MerchantMemberInvitationEntity> update(MerchantMemberInvitationEntity merchantMemberInvitationEntity) {
        return merchantMemberInvitationRepository.save(merchantMemberInvitationEntity);
    }

    @Override
    public Mono<MerchantMemberInvitationEntity> save(MerchantMemberInvitationEntity merchantMemberInvitationEntity) {
        return merchantMemberInvitationRepository.save(merchantMemberInvitationEntity);
    }

    @Override
    public Mono<MerchantMemberInvitationEntity> delete(UUID uuid) {
        return merchantMemberInvitationRepository.findById(uuid)
                .flatMap((merchantMemberInvitation -> merchantMemberInvitationRepository.deleteById(merchantMemberInvitation.getId())
                        .thenReturn(merchantMemberInvitation)));
    }
}
