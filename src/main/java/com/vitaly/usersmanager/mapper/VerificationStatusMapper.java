package com.vitaly.usersmanager.mapper;

import com.crazym8nd.commonsdto.dto.VerificationStatusDto;
import com.vitaly.usersmanager.entity.VerificationStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerificationStatusMapper extends GenericMapper<VerificationStatusDto, VerificationStatusEntity> {
}
