package com.vitaly.usersmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("person.merchant_member_invitations")
public class MerchantMemberInvitationEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EntityStatus status;
    private LocalDateTime expiresAt;
    private UUID merchantId;
    private String firstName;
    private String lastName;
    private String email;


    @Override
    public boolean isNew() {
        return (createdAt == null);
    }
}
