package com.vitaly.usersmanager.rest;

import com.crazym8nd.commonsdto.dto.IndividualDto;
import com.crazym8nd.commonsdto.dto.IndividualRegistrationDto;
import com.crazym8nd.commonsdto.dto.UpdateRequestIndividualDto;
import com.crazym8nd.commonsdto.dto.response.IndividualInfoResponse;
import com.crazym8nd.commonsdto.dto.response.RegistrationResponse;
import com.vitaly.usersmanager.exceptionhandling.UserAlreadyExistsException;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.mapper.UserMapper;
import com.vitaly.usersmanager.service.IndividualService;
import com.vitaly.usersmanager.service.PersonService;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import com.vitaly.usersmanager.service.UserService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    private final UserMapper userMapper;
    private final PersonService personService;
    private final UserActionsHistoryService userActionsHistoryService;


    @GetMapping("/{id}")
    public Mono<IndividualInfoResponse> getIndividualById(@PathVariable UUID id) {
        return individualService.getById(id)
                .map(individualMapper::toDto)
                .flatMap(individualDto -> userService.getByIdWithAddress(individualDto.getUserId())
                        .map(userMapper::toDto)
                        .map(userDto -> IndividualInfoResponse.builder()
                                .individualDto(individualDto)
                                .userDto(userDto)
                                .build())
                );
    }

    @PostMapping
    public Mono<RegistrationResponse> createIndividual(@RequestBody IndividualRegistrationDto dtoForRegistration) {
        return Mono.just(dtoForRegistration)
                .flatMap(dto -> {
                    if (!isValidDto(dto)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body"));
                    }
                    return Mono.just(dto);
                })
                .map(personService::extractUserDto)
                .flatMap(userForRegistration -> userService.save(userMapper.toEntity(userForRegistration))
                        .flatMap(savedUser -> {
                            IndividualDto individualForRegistration = personService.extractIndividualDto(dtoForRegistration);
                            individualForRegistration.setUserId(savedUser.getId());
                            return individualService.save(individualMapper.toEntity(individualForRegistration))
                                    .then(userActionsHistoryService.createHistory(Mono.empty(),savedUser))
                                    .thenReturn(RegistrationResponse.builder()
                                            .individualId(individualForRegistration.getId())
                                            .message("Individual registration is successful!")
                                            .build());
                        })
                )
                .onErrorResume(UserAlreadyExistsException.class, ex -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage())));
    }


    @DeleteMapping("/{id}")
    public Mono<?> deleteIndividual(@PathVariable UUID id) {
        return individualService.getById(id)
                .flatMap((individual) -> individualService.delete(individual.getId()))
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    @PutMapping("/{id}")
    public Mono<?> updateIndividual(@PathVariable UUID id, @RequestBody UpdateRequestIndividualDto updateIndividualDto) {
        if (id.equals(updateIndividualDto.getIndividualDto().getId())) {
            return personService.updateInfo(updateIndividualDto);
        } else {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        }
    }

    private boolean isValidDto(@Nonnull IndividualRegistrationDto dto) {
       return dto.getFirstName() != null && dto.getLastName() != null &&
       dto.getPassportNumber() != null && dto.getSecretKey() != null && dto.getEmail() != null && dto.getPhoneNumber() != null;
    }
 }
