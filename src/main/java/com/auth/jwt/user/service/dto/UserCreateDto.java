package com.auth.jwt.user.service.dto;

import com.auth.jwt.user.domain.User;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String nickName;
    private String password;
    private String etc;

    public User toEntity() {
        return User.builder()
                .nickName(nickName)
                .password(password)
                .etc(etc)
                .build();
    }
}
