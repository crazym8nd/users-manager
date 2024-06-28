package com.vitaly.usersmanager.rest;


import com.vitaly.usersmanager.dtoForCommons.IndividualRegistrationDto;
import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.dtoForCommons.UpdateRequestIndividualDto;
import com.vitaly.usersmanager.dtoForCommons.response.IndividualInfoResponse;
import com.vitaly.usersmanager.dtoForCommons.response.RegistrationResponse;
import com.vitaly.usersmanager.entity.EntityStatus;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.exceptionhandling.UserAlreadyExistsException;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.mapper.UserMapper;
import com.vitaly.usersmanager.repository.UserRepository;
import com.vitaly.usersmanager.service.IndividualService;
import com.vitaly.usersmanager.service.PersonService;
import com.vitaly.usersmanager.service.UserActionsHistoryService;
import com.vitaly.usersmanager.service.UserService;
import com.vitaly.usersmanager.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
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
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;


    @GetMapping("/{id}")
    public Mono<IndividualInfoResponse> getIndividualById(@PathVariable UUID id) {
        return individualService.getById(id)
                .map(individualMapper::toDto)
                .flatMap(individualDto -> userService.getByIdWithAddress(individualDto.getUserId())
                        .map(userMapper::toDto)
                        .map(userDto -> IndividualInfoResponse.builder()
                                .testIndividualDto(individualDto)
                                .testUserDto(userDto)
                                .build())
                );
    }

    @PostMapping
    public Mono<RegistrationResponse> createIndividual(@RequestBody IndividualRegistrationDto dtoForRegistration) {
        return Mono.just(dtoForRegistration)
                .map(personService::extractUserDto)
                .flatMap(userForRegistration -> userService.save(userMapper.toEntity(userForRegistration))
                        .flatMap(savedUser -> {
                            TestIndividualDto individualForRegistration = personService.extractIndividualDto(dtoForRegistration);
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
        if (id.equals(updateIndividualDto.getTestIndividualDto().getId())) {
            return personService.updateInfo(updateIndividualDto);
        } else {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping
    public Mono<?> test(){
        UserEntity entity = UserEntity.builder()
                .id(UUID.fromString("db697c16-ee00-470e-ac67-1b421f826fee"))
                .secretKey("testassssdasd")
                .updatedAt(LocalDateTime.now())
                .firstName("Hisaaary")
                .lastName("Explssttrer")
                .verifiedAt(LocalDateTime.now())
                .phoneNumber("89166123122")
                .email("psogggdasd@email.com")
                .status(EntityStatus.ACTIVE)
                .filled(false)
                .addressId(null)
                .build();
        Mono<UserEntity> userMono = userService.getById(UUID.fromString("db697c16-ee00-470e-ac67-1b421f826fee"));

        return userActionsHistoryService.createHistory(userMono,entity)
                .thenReturn("OK");
    }
 }
