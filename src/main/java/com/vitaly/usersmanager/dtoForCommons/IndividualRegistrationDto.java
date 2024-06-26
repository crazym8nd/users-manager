package com.vitaly.usersmanager.dtoForCommons;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualRegistrationDto {
    private String secretKey;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private UUID addressId;

    private String passportNumber;
}