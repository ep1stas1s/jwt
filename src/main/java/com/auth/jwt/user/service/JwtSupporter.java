package com.auth.jwt.user.service;

import com.auth.jwt.user.service.dto.UserInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;

@Slf4j
@Component
public class JwtSupporter {
    public static final String ID = "id";
    public static final String NICK_NAME = "nickName";

    private static final String USER = "user";
    private static final int ONE_HOUR = 60 * 60 * 1000;

    // TODO: 2019-12-30 properties...
    private static final String SECRET_KEY = "I am the KILLER WHALE";

    public String createToken(UserInfoDto userInfoDto) {
        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setSubject(USER)
                .setIssuedAt(new Date())
                .setExpiration(calculateExpiration())
                .claim(ID, userInfoDto.getId())
                .claim(NICK_NAME, userInfoDto.getNickName())
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }

    private Date calculateExpiration() {
        return new Date(System.currentTimeMillis() + ONE_HOUR);
    }

    private byte[] generateKey() {
        return SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    }

    public boolean isUsable(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private Jws<Claims> parseToken(String token) {

        return Jwts.parser()
                .setSigningKey(generateKey())
                .requireSubject(USER)
                .parseClaimsJws(token);
    }

    public Claims parseAndGetBody(String token) {
        return parseToken(token).getBody();
    }
}
