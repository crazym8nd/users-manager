package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.entity.UserActionsHistoryEntity;
import com.vitaly.usersmanager.entity.UserEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserActionsHistoryService extends GenericService<UserActionsHistoryEntity, UUID> {

    Mono<UserActionsHistoryEntity> createHistory(UserEntity userEntity);
}
