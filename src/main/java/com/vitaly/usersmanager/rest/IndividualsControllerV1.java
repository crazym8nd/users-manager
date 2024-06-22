package com.vitaly.usersmanager.rest;


import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.mapper.UserActionsHistoryMapper;
import com.vitaly.usersmanager.service.IndividualService;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
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
    private final UserActionsHistoryMapper userActionsHistoryMapper;
    private final UserActionsHistoryService userActionsHistoryService;


    @GetMapping("/{id}")
    public Mono<TestIndividualDto> getIndividualById(@PathVariable UUID id) {
        return individualService.getById(id)
                .map(individualMapper::toDto);
    }

    // do we need 409 conflict repsonse for duplciate? yes
    @PostMapping
    public Mono<?> createIndividual(@RequestBody TestIndividualDto individualDto) {
        return individualService.save(individualMapper.toEntity(individualDto))
                .map(individualMapper::toDto);
    }

    //what status we need to return? 204 yes
    @DeleteMapping("/{id}")
    public Mono<?> deleteIndividual(@PathVariable UUID id) {
        return individualService.getById(id)
                .flatMap((individual) -> individualService.delete(individual.getId()))
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    // do we need {id} path variable? yes
    @PutMapping("/{id}")
    public Mono<?> updateIndividual(@PathVariable UUID id, @RequestBody TestIndividualDto individualDto) {
        if (id == individualDto.getId()) {
            return individualService.update(individualMapper.toEntity(individualDto))
                    .map(individualMapper::toDto);
        } else {
            return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }
    }


    //testing
    @GetMapping("/test")
    public Mono<?> test() {
        //String json = "{\"id\":\"af0d4d8a-6e82-4af3-97bf-330c4a556518\"}";

        return userActionsHistoryService.getById(UUID.fromString("6c419b7b-cad5-455d-a62d-a301f8cfaecc"))
                .map(userActionsHistoryMapper::toDto);

    }

}
