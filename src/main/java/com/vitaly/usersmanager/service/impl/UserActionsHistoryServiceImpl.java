package com.vitaly.usersmanager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.UserActionsHistoryEntity;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.exceptionhandling.NotFoundException;
import com.vitaly.usersmanager.repository.UserActionsHistoryRepository;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.json.JsonConverter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionsHistoryServiceImpl implements UserActionsHistoryService {

    private final UserActionsHistoryRepository userActionsHistoryRepository;
    private final Javers javers;

    @Override
    public Mono<UserActionsHistoryEntity> getById(UUID uuid) {
        return userActionsHistoryRepository.findById(uuid)
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
        return userActionsHistoryRepository.save(userActionsHistoryEntity.toBuilder()
                .status(EntityStatus.ACTIVE)
                .build());
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

    @Override
    public Mono<UserActionsHistoryEntity> createHistory(Mono<UserEntity> beforeUpdate, UserEntity afterUpdate) {
        return beforeUpdate
                .flatMap(userBefore -> {
                            Diff diff = javers.compare(userBefore, afterUpdate);
                            userBefore = afterUpdate;
                            if (diff.hasChanges()) {
                                JsonConverter jsonConverter = javers.getJsonConverter();
                                String changes = jsonConverter.toJson(diff);
                                ObjectMapper objectMapper = new ObjectMapper();
                                try {
                                    JsonNode rootNode = objectMapper.readTree(changes);
                                    JsonNode changesNode = rootNode.get("changes");

                                    Map<String, String> changedFields = StreamSupport.stream(changesNode.spliterator(), false)
                                            .filter(node -> node.get("changeType").asText().equals("ValueChange"))
                                            .collect(Collectors.toMap(
                                                    node -> node.get("property").asText(),
                                                    node -> node.get("right").asText()
                                            ));

                                    String changedFieldsJson = objectMapper.writeValueAsString(changedFields);

                                    UserActionsHistoryEntity createdHistory = UserActionsHistoryEntity.builder()
                                            .changedValues(Json.of(changedFieldsJson))
                                            .build();
                                    return userActionsHistoryRepository.save(createdHistory);
                                } catch (JsonProcessingException e) {
                                    return Mono.error(new RuntimeException("Failed to parse Diff JSON string", e));
                                }

                            } else {
                                return Mono.just(UserActionsHistoryEntity.builder()
                                        .build());
                            }
                        }
                );
    }
}