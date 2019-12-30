package com.auth.jwt.user.controller;

import com.auth.jwt.user.service.dto.UserCreateDto;
import com.auth.jwt.user.service.dto.UserInfoDto;
import com.auth.jwt.user.service.dto.UserLoginDto;
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

    private static int INDEX = 0;

    @Autowired
    private WebTestClient webTestClient;

    private String token;
    private UserCreateDto userCreateDto;

    @BeforeEach
    void setUp() {
        userCreateDto = new UserCreateDto("whale" + INDEX++, "password", "etc");

        // Sign up
        post(API_USER, userCreateDto)
                .expectStatus().isOk();

        // Sign in & get token
        UserLoginDto userLoginDto = new UserLoginDto(userCreateDto.getNickName(), userCreateDto.getPassword());
        token = loginAndGetToken(userLoginDto);
    }

    private String loginAndGetToken(UserLoginDto userLoginDto) {
        return post(API_USER + "/login", userLoginDto)
                .expectStatus().isOk()
                .expectHeader().exists(AUTHORIZATION)
                .expectBody()
                .returnResult()
                .getResponseHeaders()
                .getFirst(AUTHORIZATION);
    }

    @Test
    void authorizedRequest() {
        UserInfoDto userInfoDto = get(API_USER + "/{userId}", INDEX)
                .expectStatus().isOk()
                .expectBody(UserInfoDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(userInfoDto.getNickName()).isEqualTo(userCreateDto.getNickName());
    }

    @Test
    void unauthorizedRequest() {
        ErrorMessage errorMessage = getWithoutLogin(API_USER + "/{userId}", INDEX)
                .expectStatus().isUnauthorized()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        assertThat(errorMessage.getMessage()).contains("권한");
    }

    @Test
    void login_fail() {
        UserLoginDto wrongUserLoginDto = new UserLoginDto("whale" + INDEX, "wrong_password");
        post(API_USER + "/login", wrongUserLoginDto)
                .expectStatus().isUnauthorized();
    }

    private WebTestClient.ResponseSpec getWithoutLogin(String uri, Object uriVariables) {
        return webTestClient.get()
                .uri(uri, uriVariables)
                .exchange();
    }

    private WebTestClient.ResponseSpec get(String uri, Object uriVariables) {
        return webTestClient.get()
                .uri(uri, uriVariables)
                .header(AUTHORIZATION, token)
                .exchange();
    }

    private <T> WebTestClient.ResponseSpec post(String uri, T dto) {
        return webTestClient.post()
                .uri(uri)
                .body(Mono.just(dto), dto.getClass())
                .exchange();
    }
}