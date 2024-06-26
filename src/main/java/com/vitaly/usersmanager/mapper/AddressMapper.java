package com.vitaly.usersmanager.mapper;

import com.crazym8nd.commonsdto.dto.AddressDto;
import com.vitaly.usersmanager.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
public interface AddressMapper extends GenericMapper<AddressDto, AddressEntity> {
}
