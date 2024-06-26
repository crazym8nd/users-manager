package com.vitaly.usersmanager.entity;

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
@Table("person.addresses")
public class AddressEntity implements Persistable<UUID> {
    @Id
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EntityStatus status;
    private Long countryId;
    private String address;
    private String zipCode;
    private String city;
    private String state;

    @Transient
    private CountryEntity country;


    @Override
    public boolean isNew() {
        return (createdAt == null);
    }
}
