package com.auth.jwt.user.controller;

import com.auth.jwt.user.service.JwtSupporter;
import com.auth.jwt.user.service.UserService;
import com.auth.jwt.user.service.dto.UserCreateDto;
import com.auth.jwt.user.service.dto.UserInfoDto;
import com.auth.jwt.user.service.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final String USER = "user";

    private final UserService userService;
    private final JwtSupporter jwtSupporter;

    public UserController(UserService userService, JwtSupporter jwtSupporter) {
        this.userService = userService;
        this.jwtSupporter = jwtSupporter;
    }

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody UserCreateDto userCreateDto) {
        userService.save(userCreateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDto> profile(@PathVariable long userId) {
        return ResponseEntity.ok(userService.findUserInfoById(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        UserInfoDto userInfoDto = userService.findUserInfoById(userLoginDto);

        return ResponseEntity.ok()
                .header(AUTHORIZATION, jwtSupporter.createToken(USER, userInfoDto))
                .build();
    }
}
