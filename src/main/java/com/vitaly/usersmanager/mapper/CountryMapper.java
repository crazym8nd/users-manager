package com.vitaly.usersmanager.mapper;

import com.crazym8nd.commonsdto.dto.CountryDto;
import com.vitaly.usersmanager.entity.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper extends GenericMapper<CountryDto, CountryEntity> {
}

