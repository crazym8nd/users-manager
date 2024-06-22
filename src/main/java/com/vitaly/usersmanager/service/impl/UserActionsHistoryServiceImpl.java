package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.entity.UserActionsHistoryEntity;
import com.vitaly.usersmanager.repository.UserActionsHistoryRepository;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionsHistoryServiceImpl implements UserActionsHistoryService {

    private final UserActionsHistoryRepository userActionsHistoryRepository;

    @Override
    public Mono<UserActionsHistoryEntity> getById(UUID uuid) {
        return userActionsHistoryRepository.findById(uuid);
    }

    @Override
    public Mono<UserActionsHistoryEntity> update(UserActionsHistoryEntity userActionsHistoryEntity) {
        return userActionsHistoryRepository.save(userActionsHistoryEntity);
    }

    @Override
    public Mono<UserActionsHistoryEntity> save(UserActionsHistoryEntity userActionsHistoryEntity) {
        return userActionsHistoryRepository.save(userActionsHistoryEntity);
    }

    @Override
    public Mono<UserActionsHistoryEntity> delete(UUID uuid) {
        return userActionsHistoryRepository.findById(uuid)
                .flatMap((userActionsHistory -> userActionsHistoryRepository.deleteById(userActionsHistory.getId())
                        .thenReturn(userActionsHistory)));
    }
}
