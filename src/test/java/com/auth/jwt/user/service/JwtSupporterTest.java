package com.auth.jwt.user.service;

import com.auth.jwt.user.service.dto.UserInfoDto;
import com.auth.jwt.user.service.exception.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class JwtSupporterTest {
    private static final JwtSupporter JWT_SUPPORTER = new JwtSupporter();

    @Test
    void createAndParseToken() {
        UserInfoDto user = new UserInfoDto(1L, "whale");
        String token = JWT_SUPPORTER.createToken("user", user);
        log.debug(token);
        assertThat(JWT_SUPPORTER.isUsable(token)).isTrue();

        Claims claims = JWT_SUPPORTER.parseAndGetBody(token);
        assertThat(claims.get(JwtSupporter.ID, Long.class)).isEqualTo(user.getId());
        assertThat(claims.get(JwtSupporter.NICK_NAME, String.class)).isEqualTo(user.getNickName());
    }

    // ExpiredJwtException : JWT를 생성할 때 지정한 유효기간 초과할 때
    // UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때
    // MalformedJwtException : JWT가 올바르게 구성되지 않았을 때
    // SignatureException : JWT의 기존 서명을 확인하지 못했을 때
    // IllegalArgumentException
    @Test
    void createAndParseToken_invalidSignature() {
        UserInfoDto user = new UserInfoDto(1L, "whale");
        String token = JWT_SUPPORTER.createToken("user", user) + "invalid";

        assertThatThrownBy(() -> JWT_SUPPORTER.isUsable(token)).isInstanceOf(UnAuthorizedException.class);
    }
}