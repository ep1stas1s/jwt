package com.auth.jwt.web.interceptor;

import com.auth.jwt.user.service.JwtSupporter;
import com.auth.jwt.user.service.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    private final JwtSupporter jwtSupporter;

    public JwtInterceptor(JwtSupporter jwtSupporter) {
        this.jwtSupporter = jwtSupporter;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTHORIZATION);

        if (isValid(token)) {
            return true;
        }
        throw new UnAuthorizedException();
    }

    private boolean isValid(String token) {
        return !Objects.isNull(token) && jwtSupporter.isUsable(token);
    }
}
