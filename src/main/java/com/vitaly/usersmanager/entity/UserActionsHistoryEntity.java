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
@Table("person.user_actions_history")
public class UserActionsHistoryEntity implements Persistable<UUID> {

    @Id
    private UUID id;
    private LocalDateTime created;
    private UUID profileId;
    private String reason;

    private String changedValues; // TODO: how to implement this type from postgres jsonb type ??

    @Override
    public boolean isNew() {
        return (id == null);
    }
}
