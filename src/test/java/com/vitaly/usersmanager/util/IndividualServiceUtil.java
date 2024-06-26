package com.vitaly.usersmanager.util;

import com.vitaly.usersmanager.entity.IndividualEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public class IndividualServiceUtil {

    public static Mono<IndividualEntity> getIndividualEntityForGetByIdTest() {
        return Mono.just(IndividualEntity.builder()
                .id(UUID.fromString("79e7a181-37f1-44a6-b323-54edb52634b7"))
                .userId(UUID.fromString("86b38d11-e9b5-433f-bd0b-c79143996a8b"))
                .passportNumber("1234123456")
                .verifiedAt(LocalDateTime.now())
                .build());
    }
}
