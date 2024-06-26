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
@Table("person.users")
public class UserEntity implements Persistable<UUID> {
    @Id
    private UUID id;

    private String secretKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EntityStatus status;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime verifiedAt;
    private boolean filled;
    private UUID addressId;


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return (createdAt == null);
    }
}
