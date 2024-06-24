package com.vitaly.usersmanager.dtoForCommons;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantMemberInvitationDto {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private UUID merchantId;
    private String firstName;
    private String lastName;
    private String email;
    private String status;

}
