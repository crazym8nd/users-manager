package com.vitaly.usersmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("person.countries")
public class CountryEntity implements Persistable<Long> {
    @Id
    private Long id;

    private LocalDateTime created;
    private LocalDateTime updated;
    private String name;
    private String alpha2;
    private String alpha3;
    private String status;

    @Override
    public boolean isNew() {
        return (id == null);
    }
}
