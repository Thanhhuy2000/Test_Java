package com.example.HotelManage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HotelManage.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByActive(Boolean active);
    
    List<User> findByRole(User.UserRole role);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
}

