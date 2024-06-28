package com.vitaly.usersmanager.util;

import com.crazym8nd.commonsdto.dto.IndividualRegistrationDto;

public class IndividualsControllerV1Util {
    public static IndividualRegistrationDto getIndividualRegistrationDtoForRegistrationTest() {
        return IndividualRegistrationDto.builder()
                .secretKey("supersasd")
                .phoneNumber("12323567890")
                .email("tes22t@mail.com")
                .firstName("testFirstName")
                .lastName("testLastName")
                .passportNumber("1234567890")
                .build();
    }

    public static IndividualRegistrationDto registerIndividualForGetByIdTest() {
        return IndividualRegistrationDto.builder()
                .secretKey("supersecasdretkey")
                .phoneNumber("1230")
                .email("tes3st@mail.com")
                .firstName("dddame")
                .lastName("tasdasdme")
                .passportNumber("13232")
                .build();
    }

    public static IndividualRegistrationDto registerIndividualForDeleteTest() {
        return IndividualRegistrationDto.builder()
                .secretKey("supwwtkey")
                .phoneNumber("125553asd4567890")
                .email("test332@mail.com")
                .firstName("tesd4dytFiarstName")
                .lastName("testLa4soodtName")
                .passportNumber("1asd44")
                .build();
    }

    public static IndividualRegistrationDto getInvalidRegistrationDto() {
        return IndividualRegistrationDto.builder()
                .build();
    }

    public static IndividualRegistrationDto registerIndividualForDeleteBRTest() {
        return IndividualRegistrationDto.builder()
                .secretKey("susdkey")
                .phoneNumber("1")
                .email("tebbbb2@mail.com")
                .firstName("hello")
                .lastName("ge")
                .passportNumber("21easdasd")
                .build();
    }

    public static IndividualRegistrationDto registerIndividualForGetByIdBRTest() {
        return IndividualRegistrationDto.builder()
                .secretKey("super555tkey")
                .phoneNumber("12322230")
                .email("tesdsdst@mail.com")
                .firstName("bnnnnname")
                .lastName("nananaane")
                .passportNumber("88888")
                .build();
    }
}
