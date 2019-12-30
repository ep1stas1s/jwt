package com.auth.jwt.user.controller;

import com.auth.jwt.user.service.dto.UserCreateDto;
import com.auth.jwt.user.service.dto.UserInfoDto;
import com.auth.jwt.web.advice.ErrorMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static final String API_USER = "/api/users";

    @Autowired
    private WebTestClient webTestClient;

    private String token;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void setUp() {
        userCreateDto = new UserCreateDto("whale", "password");

        token = post(API_USER, userCreateDto)
                .expectStatus().isOk()
                .expectHeader().exists(AUTHORIZATION)
                .expectBody()
                .returnResult()
                .getResponseHeaders()
                .getFirst(AUTHORIZATION);
    }

    @Test
    void authorizedRequest() {
        UserInfoDto userInfoDto = webTestClient.get()
                .uri(API_USER + "/{userId}", 1L)
                .header(AUTHORIZATION, token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserInfoDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(userInfoDto.getNickName()).isEqualTo(userCreateDto.getNickName());
    }

    @Test
    void unauthorizedRequest() {
        ErrorMessage errorMessage = webTestClient.get()
                .uri(API_USER + "/{userId}", 1L)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorMessage.getMessage()).contains("권한");
    }

    private <T> WebTestClient.ResponseSpec post(String uri, T dto) {
        return webTestClient.post()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }
}