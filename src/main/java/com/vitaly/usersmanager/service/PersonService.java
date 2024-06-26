package com.vitaly.usersmanager.service;

import com.vitaly.usersmanager.dtoForCommons.IndividualRegistrationDto;
import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.dtoForCommons.TestUserDto;
import com.vitaly.usersmanager.dtoForCommons.response.IndividualInfoResponse;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<IndividualInfoResponse> updateInfo(IndividualInfoResponse individualInfoResponse);
    TestIndividualDto extractIndividualDto(IndividualRegistrationDto dtoForRegistration);
    TestUserDto extractUserDto(IndividualRegistrationDto dtoForRegistration);
}
