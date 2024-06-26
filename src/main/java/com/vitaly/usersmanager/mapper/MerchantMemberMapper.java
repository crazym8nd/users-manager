package com.vitaly.usersmanager.mapper;

import com.crazym8nd.commonsdto.dto.MerchantMemberDto;
import com.vitaly.usersmanager.entity.MerchantMemberEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMemberMapper extends GenericMapper<MerchantMemberDto, MerchantMemberEntity> {
}
