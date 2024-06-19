package com.vitaly.usersmanager.rest;


import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/individuals")
@Slf4j
@RequiredArgsConstructor
public class IndividualsControllerV1 {

    private final IndividualService individualService;
    private final IndividualMapper individualMapper;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TestIndividualDto>> getIndividualById(@PathVariable UUID id) {
        return individualService.getById(id)
                .map(individualMapper::toIndividualDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
