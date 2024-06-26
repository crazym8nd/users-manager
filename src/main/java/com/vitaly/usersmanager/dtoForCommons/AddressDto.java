package com.vitaly.usersmanager.dtoForCommons;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String address;
    private String zipCode;
    private String city;
    private String state;

    private CountryDto country;

}
