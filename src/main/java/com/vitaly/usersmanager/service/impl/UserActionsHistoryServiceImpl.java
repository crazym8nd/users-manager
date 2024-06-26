package com.vitaly.usersmanager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitaly.usersmanager.entity.UserActionsHistoryEntity;
import com.vitaly.usersmanager.exceptionhandling.NotFoundException;
import com.vitaly.usersmanager.repository.UserActionsHistoryRepository;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionsHistoryServiceImpl implements UserActionsHistoryService {

    private final UserActionsHistoryRepository userActionsHistoryRepository;

    @Override
    public Mono<UserActionsHistoryEntity> getById(UUID uuid) {
        return userActionsHistoryRepository.findById(uuid)
                .doOnNext(entity -> {
                    JsonNode jsonNode = convertToJsonObject(entity.getChangedValues());
                    entity.setChangedValues(jsonNode.toString());
                })
                .switchIfEmpty(Mono.error(new NotFoundException("UserActionsHistory with id: " + uuid + " not found")));
    }

    @Override
    public Mono<UserActionsHistoryEntity> update(UserActionsHistoryEntity userActionsHistoryEntity) {
        return userActionsHistoryRepository.save(userActionsHistoryEntity.toBuilder()
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public Mono<UserActionsHistoryEntity> save(UserActionsHistoryEntity userActionsHistoryEntity) {
        String jsonString = convertToJsonString(userActionsHistoryEntity.getChangedValues());
        userActionsHistoryEntity.setChangedValues(jsonString);
        return userActionsHistoryRepository.save(userActionsHistoryEntity);
    }

    @Override
    public Mono<UserActionsHistoryEntity> delete(UUID uuid) {
        return userActionsHistoryRepository.findById(uuid)
                .flatMap((userActionsHistory -> userActionsHistoryRepository.deleteById(userActionsHistory.getId())
                        .thenReturn(userActionsHistory)));
    }

    private String convertToJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }

    private JsonNode convertToJsonObject(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON string to object", e);
        }
    }
}
