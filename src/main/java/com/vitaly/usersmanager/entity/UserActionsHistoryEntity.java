package com.vitaly.usersmanager.entity;

import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("person.user_actions_history")
public class UserActionsHistoryEntity implements Persistable<UUID> {

    @Id
    private UUID id;
    private UUID userId;
    private String reason;
    private Json changedValues;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EntityStatus status;

    @Transient
    private UserEntity user;

    @Override
    public boolean isNew() {
        return (createdAt == null);
    }
}
