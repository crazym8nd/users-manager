package com.vitaly.usersmanager.rest;

import com.crazym8nd.commonsdto.dto.IndividualRegistrationDto;
import com.crazym8nd.commonsdto.dto.response.IndividualInfoResponse;
import com.crazym8nd.commonsdto.dto.response.RegistrationResponse;
import com.vitaly.usersmanager.util.IndividualsControllerV1Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class IndividualsControllerV1IntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    // happy path
    @Test
    @DisplayName("Should return individual id and success message")
    public void givenValidIndividualDto_whenRequestCreateIndividual_thenOKResponse() {
        // Given
        IndividualRegistrationDto dto = IndividualsControllerV1Util.getIndividualRegistrationDtoForRegistrationTest();

        // When
        WebTestClient.ResponseSpec result = webTestClient.post().uri("/api/v1/individuals")
                .body(Mono.just(dto), IndividualRegistrationDto.class)
                .exchange();

        // Then
        result.expectStatus().isOk()
                .expectBody()
                .jsonPath("$.individual_id").exists()
                .jsonPath("$.message").isEqualTo("Individual registration is successful!");
    }

    @Test
    @DisplayName("Should return individual with valid id")
    public void givenValidIndividualId_whenRequestGetIndividual_thenOKResponse() {
        // Given
        IndividualRegistrationDto dto = IndividualsControllerV1Util.registerIndividualForGetByIdTest();

        // Create a new individual
        EntityExchangeResult<RegistrationResponse> response = webTestClient.post().uri("/api/v1/individuals")
                .body(Mono.just(dto), IndividualRegistrationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponse.class)
                .returnResult();

        RegistrationResponse registrationResponse = response.getResponseBody();
        UUID individualId = registrationResponse.getIndividualId();


        // When
        WebTestClient.ResponseSpec result = webTestClient.get().uri("/api/v1/individuals/" + individualId)
                .exchange();

        // Then
        result.expectStatus().isOk()
                .expectBody(IndividualInfoResponse.class);
    }
    @Test
    @DisplayName("Should delete individual with valid id")
    public void givenValidIndividualId_whenRequestDeleteIndividual_thenOKResponse() {
        // Given
        IndividualRegistrationDto dto = IndividualsControllerV1Util.registerIndividualForDeleteTest();

        // Create a new individual
        EntityExchangeResult<RegistrationResponse> response = webTestClient.post().uri("/api/v1/individuals")
                .body(Mono.just(dto), IndividualRegistrationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponse.class)
                .returnResult();

        RegistrationResponse registrationResponse = response.getResponseBody();
        UUID individualId = registrationResponse.getIndividualId();

        // When
        WebTestClient.ResponseSpec result = webTestClient.delete().uri("/api/v1/individuals/" + individualId)
                .exchange();

        // Then
        result.expectStatus().isOk();
    }

    //negative path
    @Test
    @DisplayName("Should return bad request")
    public void givenInvalidIndividualDto_whenRequestCreateIndividual_thenBadRequest() {
        // Given
        IndividualRegistrationDto dto = IndividualsControllerV1Util.getInvalidRegistrationDto();

        // When
        WebTestClient.ResponseSpec result = webTestClient.post().uri("/api/v1/individuals")
                .body(Mono.just(dto), IndividualRegistrationDto.class)
                .exchange();

        // Then
        result.expectStatus().isBadRequest();
    }
    @Test
    @DisplayName("Should return bad request for get request with invalid id")
    public void givenInvalidIndividualId_whenRequestGetIndividual_thenBadRequest() {
        // Given
        IndividualRegistrationDto dto = IndividualsControllerV1Util.registerIndividualForGetByIdBRTest();

        // Create a new individual
        EntityExchangeResult<RegistrationResponse> response = webTestClient.post().uri("/api/v1/individuals")
                .body(Mono.just(dto), IndividualRegistrationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponse.class)
                .returnResult();

        String individualId = "invalidId";


        // When
        WebTestClient.ResponseSpec result = webTestClient.get().uri("/api/v1/individuals/" + individualId)
                .exchange();

        // Then
        result.expectStatus().isBadRequest();
    }
    @Test
    @DisplayName("Should return bad request for delete request with invalid id")
    public void givenInvalidIndividualId_whenRequestDeleteIndividual_thenBadRequest() {
        // Given
        IndividualRegistrationDto dto = IndividualsControllerV1Util.registerIndividualForDeleteBRTest();

        // Create a new individual
        EntityExchangeResult<RegistrationResponse> response = webTestClient.post().uri("/api/v1/individuals")
                .body(Mono.just(dto), IndividualRegistrationDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RegistrationResponse.class)
                .returnResult();

        String individualId = "invalididdidid";

        // When
        WebTestClient.ResponseSpec result = webTestClient.delete().uri("/api/v1/individuals/" + individualId)
                .exchange();

        // Then
        result.expectStatus().isBadRequest();
    }
}
