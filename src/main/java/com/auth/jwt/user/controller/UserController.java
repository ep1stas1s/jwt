package com.auth.jwt.user.controller;

import com.auth.jwt.user.service.JwtService;
import com.auth.jwt.user.service.UserService;
import com.auth.jwt.user.service.dto.UserCreateDto;
import com.auth.jwt.user.service.dto.UserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserCreateDto userCreateDto) {
        UserInfoDto savedUserInfo = userService.save(userCreateDto);

        return ResponseEntity.ok().build();
    }
}
