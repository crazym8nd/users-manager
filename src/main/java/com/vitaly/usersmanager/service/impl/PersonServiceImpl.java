package com.vitaly.usersmanager.service.impl;

import com.crazym8nd.commonsdto.dto.IndividualDto;
import com.crazym8nd.commonsdto.dto.IndividualRegistrationDto;
import com.crazym8nd.commonsdto.dto.UpdateRequestIndividualDto;
import com.crazym8nd.commonsdto.dto.UserDto;
import com.vitaly.usersmanager.entity.AddressEntity;
import com.vitaly.usersmanager.entity.IndividualEntity;
import com.vitaly.usersmanager.entity.UserEntity;
import com.vitaly.usersmanager.mapper.IndividualMapper;
import com.vitaly.usersmanager.mapper.UserMapper;
import com.vitaly.usersmanager.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final AddressService addressService;
    private final CountryService countryService;

    @Override
    @Transactional
    public Mono<UpdateRequestIndividualDto> updateInfo(UpdateRequestIndividualDto updateReqeustIndividualDto) {
        UUID userId = updateReqeustIndividualDto.getUserDto().getId();
        UUID individualId = updateReqeustIndividualDto.getIndividualDto().getId();

        UserEntity newUserInfo = userMapper.toEntity(updateReqeustIndividualDto.getUserDto());
        IndividualEntity newIndividualInfo = individualMapper.toEntity(updateReqeustIndividualDto.getIndividualDto());
        AddressEntity newAddressInfo = newUserInfo.getAddress();
        String name = newUserInfo.getAddress().getCountry().getName();


        return countryService.findByName(name)
                .map(country -> {
                    newAddressInfo.setCountry(country);
                    newAddressInfo.setCountryId(country.getId());
                    return newAddressInfo;
                })
                .flatMap(addressService::save)
                .flatMap(savedAddress -> {
                    newUserInfo.setAddress(savedAddress);
                    return userService.getByIdWithAddress(userId);
                })
                .flatMap(userToBeUpdated -> userService.update(
                        userToBeUpdated.toBuilder()
                                .secretKey(newUserInfo.getSecretKey())
                                .phoneNumber(newUserInfo.getPhoneNumber())
                                .email(newUserInfo.getEmail())
                                .firstName(newUserInfo.getFirstName())
                                .lastName(newUserInfo.getLastName())
                                .addressId(newUserInfo.getAddress().getId())
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
    public UserDto extractUserDto(IndividualRegistrationDto dtoForRegistration) {
        return UserDto.builder()
                .id(UUID.randomUUID())
                .secretKey(dtoForRegistration.getSecretKey())
                .phoneNumber(dtoForRegistration.getPhoneNumber())
                .email(dtoForRegistration.getEmail())
                .firstName(dtoForRegistration.getFirstName())
                .lastName(dtoForRegistration.getLastName())
                .build();
    }

    @Override
    public IndividualDto extractIndividualDto(IndividualRegistrationDto dtoForRegistration) {
        return IndividualDto.builder()
                .id(UUID.randomUUID())
                .passportNumber(dtoForRegistration.getPassportNumber())
                .build();
    }
}
