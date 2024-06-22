package com.vitaly.usersmanager.mapper;

import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.entity.IndividualEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndividualMapper extends GenericMapper<TestIndividualDto, IndividualEntity> {

}
