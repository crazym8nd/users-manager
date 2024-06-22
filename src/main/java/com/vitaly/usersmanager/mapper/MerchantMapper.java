package com.vitaly.usersmanager.mapper;

import com.vitaly.usersmanager.dtoForCommons.MerchantDto;
import com.vitaly.usersmanager.entity.MerchantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMapper extends GenericMapper<MerchantDto, MerchantEntity> {
}
