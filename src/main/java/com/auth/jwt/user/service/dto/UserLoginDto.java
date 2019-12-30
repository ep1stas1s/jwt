package com.auth.jwt.user.service.dto;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private String nickName;
    private String password;
}
