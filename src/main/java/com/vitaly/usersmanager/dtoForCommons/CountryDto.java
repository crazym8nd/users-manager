package com.vitaly.usersmanager.dtoForCommons;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String alpha2;
    private String alpha3;
    private String status;

}
