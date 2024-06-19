package com.vitaly.usersmanager.rest;


import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.service.IndividualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                .map(ResponseEntity::ok);
    }

    //do we need to return 201 with location uri?
    // do we need 409 conflict repsonse for duplciate?
    @PostMapping
    public Mono<ResponseEntity<TestIndividualDto>> createIndividual(@RequestBody TestIndividualDto individualDto) {
        return individualService.save(individualMapper.toIndividualEntity(individualDto))
                .map(individualMapper::toIndividualDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    //what status we need to return? 204
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteIndividual(@PathVariable UUID id) {
        return individualService.getById(id)
                .flatMap((individual) -> individualService.delete(individual.getId()))
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // do we need {id} path variable?
    @PutMapping
    public Mono<ResponseEntity<TestIndividualDto>> updateIndividual(@RequestBody TestIndividualDto individualDto) {
        return individualService.update(individualMapper.toIndividualEntity(individualDto))
                .map(individualMapper::toIndividualDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
