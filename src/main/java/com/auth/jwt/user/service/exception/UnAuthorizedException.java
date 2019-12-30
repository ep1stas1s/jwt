package com.auth.jwt.user.service.exception;

public class UnAuthorizedException extends RuntimeException {
    private static final String MESSAGE = "권한이 없습니다.";
    public UnAuthorizedException() {
        super(MESSAGE);
    }
}
