package com.vitaly.usersmanager.mapper;

import com.vitaly.usersmanager.dtoForCommons.UserActionsHistoryDto;
import com.vitaly.usersmanager.entity.UserActionsHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserActionsHistoryMapper extends GenericMapper<UserActionsHistoryDto, UserActionsHistoryEntity> {
}
