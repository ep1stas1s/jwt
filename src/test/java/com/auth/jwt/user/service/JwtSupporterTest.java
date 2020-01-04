package com.auth.jwt.user.service;

import com.auth.jwt.user.service.dto.UserInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.auth.jwt.user.service.JwtSupporter.ID;
import static com.auth.jwt.user.service.JwtSupporter.NICK_NAME;
import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class JwtSupporterTest {
    private static final JwtSupporter JWT_SUPPORTER = new JwtSupporter();

    @Test
    void createAndParseToken() {
        UserInfoDto user = new UserInfoDto(1L, "whale");
        String token = JWT_SUPPORTER.createToken(user);
        log.debug("token : {}", token);
        assertThat(JWT_SUPPORTER.isUsable(token)).isTrue();

        Claims claims = JWT_SUPPORTER.parseAndGetBody(token);
        log.debug("claims : {}", claims);
        assertThat(claims.get(ID, Long.class)).isEqualTo(user.getId());
        assertThat(claims.get(NICK_NAME, String.class)).isEqualTo(user.getNickName());
    }

    // JWT x.y.z
    // ExpiredJwtException : 지정한 유효기간(expiration) 초과할 때
    // UnsupportedJwtException : 예상하는 형식과 일치하지 않는 특정 형식이나 구성의 JWT일 때 (잘 모르겠음)
    // MalformedJwtException : JSON Web Token 이 올바르게 구성되지 않았을 때 (x,y)
    // SignatureException : 기존 서명을 확인하지 못했을 때 (z)
    @Test
    void createAndParseToken_false_invalidSignature() {
        UserInfoDto user = new UserInfoDto(1L, "whale");
        String token = JWT_SUPPORTER.createToken(user) + "invalid";

        assertThat(JWT_SUPPORTER.isUsable(token)).isFalse();
    }
}