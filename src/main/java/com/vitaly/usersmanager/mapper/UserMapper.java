package com.vitaly.usersmanager.mapper;


import com.crazym8nd.commonsdto.dto.UserDto;
import com.vitaly.usersmanager.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper extends GenericMapper<UserDto, UserEntity> {
}
