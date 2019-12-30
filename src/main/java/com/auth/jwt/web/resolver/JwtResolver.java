package com.auth.jwt.web.resolver;

import com.auth.jwt.user.annotation.JwtUser;
import com.auth.jwt.user.service.JwtSupporter;
import com.auth.jwt.user.service.dto.UserInfoDto;
import com.auth.jwt.web.interceptor.JwtInterceptor;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtResolver implements HandlerMethodArgumentResolver {
    private final JwtSupporter jwtSupporter;

    public JwtResolver(JwtSupporter jwtSupporter) {
        this.jwtSupporter = jwtSupporter;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader(AUTHORIZATION);

        return parseTokenToUserInfoDto(token);
    }

    private UserInfoDto parseTokenToUserInfoDto(String token) {
        Claims claims = jwtSupporter.parseAndGetBody(token);
        return UserInfoDto.builder()
                .id(claims.get(JwtSupporter.ID, Long.class))
                .nickName(claims.get(JwtSupporter.NICK_NAME, String.class))
                .build();
    }
}
