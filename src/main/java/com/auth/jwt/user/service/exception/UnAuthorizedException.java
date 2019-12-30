package com.auth.jwt.user.service.exception;

import com.auth.jwt.web.exception.BadRequestException;

public class UnAuthorizedException extends BadRequestException {
    private static final String MESSAGE = "권한이 없습니다.";

    public UnAuthorizedException() {
        super(MESSAGE);
    }
}
