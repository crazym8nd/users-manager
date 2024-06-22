package com.vitaly.usersmanager.mapper;

import com.vitaly.usersmanager.dtoForCommons.TestUserDto;
import com.vitaly.usersmanager.entity.UserEntity;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<TestUserDto, UserEntity> {

}
