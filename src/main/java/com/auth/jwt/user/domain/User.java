package com.auth.jwt.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NICK_NAME", nullable = false, length = 50)
    private String nickName;

    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;

    @Column(name = "ETC", length = 255)
    private String etc;
}
