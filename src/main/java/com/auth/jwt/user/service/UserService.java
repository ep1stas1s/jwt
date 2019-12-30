package com.auth.jwt.user.service;

import com.auth.jwt.user.domain.User;
import com.auth.jwt.user.domain.repository.UserRepository;
import com.auth.jwt.user.service.dto.UserCreateDto;
import com.auth.jwt.user.service.dto.UserInfoDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfoDto save(UserCreateDto userCreateDto) {
        User savedUser = userRepository.save(userCreateDto.toEntity());
        return UserInfoDto.from(savedUser);
    }
}
