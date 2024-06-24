package com.vitaly.usersmanager.rest;


import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.mapper.UserActionsHistoryMapper;
import com.vitaly.usersmanager.service.IndividualService;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import com.vitaly.usersmanager.service.UserService;
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
    private final UserService userService;
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
        //String json = "{\"id\":\"3943c704-f9ae-473b-a282-ad99a2ead223\"}";

        return userService.getById(UUID.fromString("3943c704-f9ae-473b-a282-ad99a2ead223"));

    }

}
