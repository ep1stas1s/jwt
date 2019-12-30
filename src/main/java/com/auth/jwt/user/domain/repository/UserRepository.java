package com.auth.jwt.user.domain.repository;

import com.auth.jwt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickNameAndPassword(String nickName, String password);
}
