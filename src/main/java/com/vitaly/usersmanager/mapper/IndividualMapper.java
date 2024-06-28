package com.vitaly.usersmanager.mapper;


import com.crazym8nd.commonsdto.dto.IndividualDto;
import com.vitaly.usersmanager.entity.IndividualEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndividualMapper extends GenericMapper<IndividualDto, IndividualEntity> {

}
