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
@Table("person.merchant_members")
public class MerchantMemberEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private UUID userId;
    private UUID merchantId;
    private String memberRole;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EntityStatus status;

    @Override
    public boolean isNew() {
        return (createdAt == null);
    }
}
