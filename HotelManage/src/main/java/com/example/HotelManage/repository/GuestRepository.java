package com.example.HotelManage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HotelManage.model.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    
    Optional<Guest> findByIdentityCard(String identityCard);
    
    Optional<Guest> findByEmail(String email);
    
    List<Guest> findByPhone(String phone);
    
    @Query("SELECT g FROM Guest g WHERE LOWER(g.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Guest> searchByName(@Param("name") String name);
    
    @Query("SELECT g FROM Guest g WHERE LOWER(g.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR g.phone LIKE CONCAT('%', :keyword, '%') " +
           "OR g.email LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR g.identityCard LIKE CONCAT('%', :keyword, '%')")
    List<Guest> searchByKeyword(@Param("keyword") String keyword);
    
    boolean existsByIdentityCard(String identityCard);
}

