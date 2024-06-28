package com.vitaly.usersmanager.service;


import com.crazym8nd.commonsdto.dto.IndividualDto;
import com.crazym8nd.commonsdto.dto.IndividualRegistrationDto;
import com.crazym8nd.commonsdto.dto.UpdateRequestIndividualDto;
import com.crazym8nd.commonsdto.dto.UserDto;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<UpdateRequestIndividualDto> updateInfo(UpdateRequestIndividualDto updateRequestIndividualDto);

    IndividualDto extractIndividualDto(IndividualRegistrationDto dtoForRegistration);

    UserDto extractUserDto(IndividualRegistrationDto dtoForRegistration);
}
