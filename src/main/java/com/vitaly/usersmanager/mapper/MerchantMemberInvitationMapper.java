package com.vitaly.usersmanager.mapper;


import com.crazym8nd.commonsdto.dto.MerchantMemberInvitationDto;
import com.vitaly.usersmanager.entity.MerchantMemberInvitationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMemberInvitationMapper extends GenericMapper<MerchantMemberInvitationDto, MerchantMemberInvitationEntity> {
}
