package com.auth.jwt.user.service.exception;

import com.auth.jwt.web.exception.BadRequestException;

public class NotFoundUserException extends BadRequestException {
    private static final String MESSAGE = "회원을 찾을 수 없습니다.";

    public NotFoundUserException() {
        super(MESSAGE);
    }
}
