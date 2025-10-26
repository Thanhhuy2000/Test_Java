package com.example.HotelManage.model;

import java.math.BigDecimal;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Số phòng không được để trống")
    @Column(unique = true, nullable = false)
    private String roomNumber;
    
    @NotNull(message = "Loại phòng không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;
    
    @NotNull(message = "Giá phòng không được để trống")
    @Min(value = 0, message = "Giá phòng phải lớn hơn 0")
    @Column(nullable = false)
    private BigDecimal pricePerNight;
    
    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status = RoomStatus.AVAILABLE;
    
    @Min(value = 1, message = "Số người tối đa phải ít nhất là 1")
    private Integer maxOccupancy;
    
    private Integer floor;
    
    @Column(length = 1000)
    private String description;
    
    private String amenities; // Comma-separated: WiFi, TV, AC, Mini-bar, etc.
    
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
    
    public enum RoomType {
        SINGLE("Phòng Đơn"),
        DOUBLE("Phòng Đôi"),
        SUITE("Phòng Suite"),
        DELUXE("Phòng Deluxe"),
        PRESIDENTIAL("Phòng Tổng Thống");
        
        private final String displayName;
        
        RoomType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum RoomStatus {
        AVAILABLE("Còn trống"),
        OCCUPIED("Đã thuê"),
        MAINTENANCE("Bảo trì"),
        CLEANING("Đang dọn dẹp"),
        RESERVED("Đã đặt");
        
        private final String displayName;
        
        RoomStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

