package com.vitaly.usersmanager.dtoForCommons.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.dtoForCommons.TestUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualInfoResponse {
    private TestUserDto testUserDto;
    private TestIndividualDto testIndividualDto;
}
