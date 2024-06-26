package com.vitaly.usersmanager.service.impl;

import com.vitaly.usersmanager.dtoForCommons.IndividualRegistrationDto;
import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.dtoForCommons.TestUserDto;
import com.vitaly.usersmanager.dtoForCommons.UpdateRequestIndividualDto;
import com.vitaly.usersmanager.entity.IndividualEntity;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.mapper.UserMapper;
import com.vitaly.usersmanager.service.IndividualService;
import com.vitaly.usersmanager.service.PersonService;
import com.vitaly.usersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final IndividualService individualService;
    private final IndividualMapper individualMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public Mono<UpdateRequestIndividualDto> updateInfo(UpdateRequestIndividualDto updateReqeustIndividualDto) {
        UUID userId = updateReqeustIndividualDto.getTestUserDto().getId();
        UUID individualId = updateReqeustIndividualDto.getTestIndividualDto().getId();

        UserEntity newUserInfo = userMapper.toEntity(updateReqeustIndividualDto.getTestUserDto());
        IndividualEntity newIndividualInfo = individualMapper.toEntity(updateReqeustIndividualDto.getTestIndividualDto());

        return userService.getByIdWithAddress(userId)
                .flatMap(userToBeUpdated -> userService.update(
                        userToBeUpdated.toBuilder()
                                .secretKey(newUserInfo.getSecretKey())
                                .phoneNumber(newUserInfo.getPhoneNumber())
                                .email(newUserInfo.getEmail())
                                .firstName(newUserInfo.getFirstName())
                                .lastName(newUserInfo.getLastName())
                                .build()
                ))
                .then(individualService.getById(individualId)
                        .flatMap(individualTobeUpdated -> individualService.update(
                                individualTobeUpdated.toBuilder()
                                        .passportNumber(newIndividualInfo.getPassportNumber())
                                        .build()
                        )))
                .thenReturn(updateReqeustIndividualDto);
        //  .onErrorMap(throwable -> new FailedToUpdateInfoException("Failed to update info"));
    }

    @Override
    public TestUserDto extractUserDto(IndividualRegistrationDto dtoForRegistration) {
        return TestUserDto.builder()
                .id(UUID.randomUUID())
                .secretKey(dtoForRegistration.getSecretKey())
                .phoneNumber(dtoForRegistration.getPhoneNumber())
                .email(dtoForRegistration.getEmail())
                .firstName(dtoForRegistration.getFirstName())
                .lastName(dtoForRegistration.getLastName())
                .build();
    }

    @Override
    public TestIndividualDto extractIndividualDto(IndividualRegistrationDto dtoForRegistration) {
        return TestIndividualDto.builder()
                .id(UUID.randomUUID())
                .passportNumber(dtoForRegistration.getPassportNumber())
                .build();
    }
}
