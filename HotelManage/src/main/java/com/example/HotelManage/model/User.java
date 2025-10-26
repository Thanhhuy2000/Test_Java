package com.example.HotelManage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username không được để trống")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotBlank(message = "Password không được để trống")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;
    
    @Email(message = "Email không hợp lệ")
    @Column(unique = true)
    private String email;
    
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CUSTOMER;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    private String position; // Chức vụ cho nhân viên
    
    private String department; // Phòng ban cho nhân viên
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum UserRole {
        MASTER_ADMIN("Chủ khách sạn", 3),
        ADMIN("Nhân viên", 2),
        CUSTOMER("Khách hàng", 1);
        
        private final String displayName;
        private final int level;
        
        UserRole(String displayName, int level) {
            this.displayName = displayName;
            this.level = level;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public int getLevel() {
            return level;
        }
        
        public boolean hasHigherOrEqualPrivilege(UserRole other) {
            return this.level >= other.level;
        }
    }
}

