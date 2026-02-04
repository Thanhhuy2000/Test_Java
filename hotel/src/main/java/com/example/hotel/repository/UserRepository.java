package com.example.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hotel.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    public User findByVerificationCode(String code);
    public User findByResetPasswordToken(String token);
}
