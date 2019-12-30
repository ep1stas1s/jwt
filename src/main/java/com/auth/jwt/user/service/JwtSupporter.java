package com.auth.jwt.user.service;

import com.auth.jwt.user.service.dto.UserInfoDto;
import com.auth.jwt.user.service.exception.UnAuthorizedException;
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

    private static final int ONE_HOUR = 60 * 60 * 1000;

    // TODO: 2019-12-30 properties...
    private static final String SECRET_KEY = "I am the KILLER WHALE";
    private static final String REG_DATE = "regDate";

    public String createToken(String subject, UserInfoDto userInfoDto) {
        return Jwts.builder()
                .setHeaderParam(TYPE, JWT_TYPE)
                .setHeaderParam(REG_DATE, System.currentTimeMillis())
                .setSubject(subject)
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
            // TODO: 2019-12-30 한 번에 boolean 리턴하는 방법?
            Jws<Claims> claims = parseToken(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UnAuthorizedException();
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token);
    }

    public Claims parseAndGetBody(String token) {
        return parseToken(token).getBody();
    }
}
