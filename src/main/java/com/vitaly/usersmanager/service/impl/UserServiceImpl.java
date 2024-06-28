package com.vitaly.usersmanager.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.exceptionhandling.NotFoundException;
import com.vitaly.usersmanager.exceptionhandling.UserAlreadyExistsException;
import com.vitaly.usersmanager.repository.UserRepository;
import com.vitaly.usersmanager.service.AddressService;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import com.vitaly.usersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.json.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AddressService addressService;
    private final UserRepository userRepository;
    private final UserActionsHistoryService userActionsHistoryService;

    @Autowired
    private Javers javers;
    @Override
    public Mono<UserEntity> getById(UUID uuid) {
        return userRepository.findById(uuid)
                .doOnNext(entity -> log.warn("Entity from repository: {}", entity))
                .map(user -> user);
    }

    @Override
    public Mono<UserEntity> getByIdWithAddress(UUID uuid) {
        return userRepository.findById(uuid)
                .flatMap(userEntity -> {
                    if (userEntity.getAddressId() != null) {
                        return addressService.getByIdWithCountry(userEntity.getAddressId())
                                .map(addressEntity -> {
                                    userEntity.setAddress(addressEntity);
                                    return userEntity;
                                })
                                .switchIfEmpty(Mono.error(new NotFoundException("Address not found")));
                    } else {
                        return Mono.just(userEntity);
                    }
                });
    }

    @Override
    public Flux<UserEntity> getAllNotFilledUsers() {
        return userRepository.findAllByStatus(EntityStatus.ACTIVE).filter(userEntity -> !userEntity.isFilled());
    }

    @Override
    public Mono<UserEntity> update(UserEntity userEntity) {

        return userActionsHistoryService.createHistory(userRepository.findById(userEntity.getId()), userEntity)
                .then(userRepository.save(userEntity));
//        return userRepository.findById(userEntity.getId())
//                .flatMap(userBefore -> {
//                    UserEntity updatedUser = userBefore.toBuilder()
//                            .secretKey(userEntity.getSecretKey() != null && !userBefore.getSecretKey().equals(userEntity.getSecretKey()) ? userEntity.getSecretKey() : userBefore.getSecretKey())
//                            .status(userEntity.getStatus() != null && !userBefore.getStatus().equals(userEntity.getStatus()) ? userEntity.getStatus() : userBefore.getStatus())
//                            .phoneNumber(userEntity.getPhoneNumber() != null && !userBefore.getPhoneNumber().equals(userEntity.getPhoneNumber()) ? userEntity.getPhoneNumber() : userBefore.getPhoneNumber())
//                            .email(userEntity.getEmail() != null && !userBefore.getEmail().equals(userEntity.getEmail()) ? userEntity.getEmail() : userBefore.getEmail())
//                            .firstName(userEntity.getFirstName() != null && !userBefore.getFirstName().equals(userEntity.getFirstName()) ? userEntity.getFirstName() : userBefore.getFirstName())
//                            .lastName(userEntity.getLastName() != null && !userBefore.getLastName().equals(userEntity.getLastName()) ? userEntity.getLastName() : userBefore.getLastName())
//                            .verifiedAt(userEntity.getVerifiedAt() != null && !userBefore.getVerifiedAt().equals(userEntity.getVerifiedAt()) ? userEntity.getVerifiedAt() : userBefore.getVerifiedAt())
//                            .addressId(userEntity.getAddressId() != null && !userBefore.getAddressId().equals(userEntity.getAddressId()) ? userEntity.getAddressId() : userBefore.getAddressId())
//                            .build();
//
//                    return userActionsHistoryService.createHistory(updatedUser)
//                            .then(userRepository.save(updatedUser));
//                });
    }

    @Override
    public Mono<UserEntity> save(UserEntity userEntity) {
        return checkEmailForUnique(userEntity.getEmail())
                .then(checkPhoneForUnique(userEntity.getPhoneNumber()))
                .then(userRepository.save(userEntity.toBuilder()
                                .status(EntityStatus.ACTIVE)
                        .build()));
    }

    @Override
    public Mono<UserEntity> delete(UUID uuid) {
        return userRepository.findById(uuid)
                .flatMap((user -> userRepository.deleteById(user.getId()).thenReturn(user)));
    }

    @Override
    public Mono<Boolean> checkEmailForUnique(String email) {
        return userRepository.existsByEmail(email)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException("You already registered. Please login."));
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    @Override
    public Mono<Boolean> checkPhoneForUnique(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException("You already registered. Please login."));
                    } else {
                        return Mono.just(false);
                    }
                });
    }

    private Mono<UserEntity> getUpdatedFields(UserEntity oldStateEntity, UserEntity newStateEntity) {
        if(oldStateEntity.getId().equals(newStateEntity.getId())) {
            return Mono.just(UserEntity.builder()
                            .secretKey(oldStateEntity.getSecretKey().equals(newStateEntity.getSecretKey()) ? null: newStateEntity.getSecretKey())
                            .status(oldStateEntity.getStatus().equals(newStateEntity.getStatus()) ? null: newStateEntity.getStatus())
                            .phoneNumber(oldStateEntity.getPhoneNumber().equals(newStateEntity.getPhoneNumber()) ? null: newStateEntity.getPhoneNumber())
                            .email(oldStateEntity.getEmail().equals(newStateEntity.getEmail()) ? null: newStateEntity.getEmail())
                            .firstName(oldStateEntity.getFirstName().equals(newStateEntity.getFirstName()) ? null: newStateEntity.getFirstName())
                            .lastName(oldStateEntity.getLastName().equals(newStateEntity.getLastName()) ? null: newStateEntity.getLastName())
                            .verifiedAt(oldStateEntity.getVerifiedAt().equals(newStateEntity.getVerifiedAt()) ? null: newStateEntity.getVerifiedAt())
                            .addressId(oldStateEntity.getAddressId().equals(newStateEntity.getAddressId()) ? null: newStateEntity.getAddressId())
                    .build());
        }
        return Mono.error(new RuntimeException("WRONG ENTITIES"));
    }


    private Mono<UserEntity> testVersionUser(UUID id, UserEntity updatedUser){
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("User with id: " + id + " not found")))
                .flatMap(user -> {
                    Diff diff = javers.compare(user, updatedUser);
                    user.setUpdatedAt(LocalDateTime.now());
                    user.setStatus(updatedUser.getStatus());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setEmail(updatedUser.getEmail());
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    user.setVerifiedAt(updatedUser.getVerifiedAt());
                    user.setFilled(updatedUser.isFilled());
                    user.setAddressId(updatedUser.getAddressId());

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
                            log.info(changedFieldsJson);
                        } catch (JsonProcessingException e) {
                            return Mono.error(new RuntimeException("Failed to parse Diff JSON string", e));
                        }


                    }
                        return Mono.just(updatedUser);
                });
    }

    }
