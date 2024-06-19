package com.vitaly.usersmanager.mapper;

import com.vitaly.usersmanager.dtoForCommons.TestIndividualDto;
import com.vitaly.usersmanager.entity.IndividualEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndividualMapper {

    TestIndividualDto toIndividualDto(IndividualEntity individualEntity);

    @InheritInverseConfiguration
    IndividualEntity toIndividualEntity(TestIndividualDto individualDto);
}
