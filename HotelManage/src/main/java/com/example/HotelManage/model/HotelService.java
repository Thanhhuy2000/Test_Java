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
@Table(name = "hotel_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelService {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tên dịch vụ không được để trống")
    @Column(nullable = false)
    private String serviceName;
    
    @NotNull(message = "Loại dịch vụ không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;
    
    @Column(length = 1000)
    private String description;
    
    @NotNull(message = "Giá dịch vụ không được để trống")
    @Min(value = 0, message = "Giá dịch vụ phải lớn hơn 0")
    @Column(nullable = false)
    private BigDecimal price;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceUnit unit = ServiceUnit.PER_USE;
    
    @Column(nullable = false)
    private Boolean available = true;
    
    private String imageUrl;
    
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
    
    public enum ServiceType {
        SPA("Spa & Massage"),
        RESTAURANT("Nhà hàng"),
        LAUNDRY("Giặt là"),
        GYM("Phòng gym"),
        POOL("Hồ bơi"),
        TRANSPORT("Vận chuyển"),
        TOUR("Tour du lịch"),
        MEETING_ROOM("Phòng họp"),
        OTHER("Khác");
        
        private final String displayName;
        
        ServiceType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public enum ServiceUnit {
        PER_USE("Theo lần"),
        PER_HOUR("Theo giờ"),
        PER_DAY("Theo ngày"),
        PER_PERSON("Theo người");
        
        private final String displayName;
        
        ServiceUnit(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

