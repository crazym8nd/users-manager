package com.vitaly.usersmanager.mapper;

import com.vitaly.usersmanager.dtoForCommons.CountryDto;
import com.vitaly.usersmanager.entity.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper extends GenericMapper<CountryDto, CountryEntity> {
}

