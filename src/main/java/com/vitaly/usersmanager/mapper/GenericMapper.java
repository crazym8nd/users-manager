package com.vitaly.usersmanager.mapper;

import org.mapstruct.InheritInverseConfiguration;

public interface GenericMapper<D, E> {
    E toEntity(D dto);

    @InheritInverseConfiguration
    D toDto(E entity);
}