package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.dtoForCommons.IndividualRegistrationDto;
import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.dtoForCommons.TestUserDto;
import com.vitaly.usersmanager.dtoForCommons.UpdateRequestIndividualDto;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<UpdateRequestIndividualDto> updateInfo(UpdateRequestIndividualDto updateRequestIndividualDto);

    TestIndividualDto extractIndividualDto(IndividualRegistrationDto dtoForRegistration);

    TestUserDto extractUserDto(IndividualRegistrationDto dtoForRegistration);
}
