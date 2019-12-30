package com.auth.jwt.user.service.dto;

import com.auth.jwt.user.domain.User;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private long id;
    private String nickName;

    public static UserInfoDto from(User user) {
        return UserInfoDto.builder()
                .id(user.getId())
                .nickName(user.getNickName())
                .build();
    }
}
