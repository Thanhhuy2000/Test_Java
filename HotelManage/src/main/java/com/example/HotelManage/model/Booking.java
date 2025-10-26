package com.example.HotelManage.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String bookingCode;
    
    @NotNull(message = "Khách hàng không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;
    
    @NotNull(message = "Phòng không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    @NotNull(message = "Ngày check-in không được để trống")
    @Column(nullable = false)
    private LocalDate checkInDate;
    
    @NotNull(message = "Ngày check-out không được để trống")
    @Column(nullable = false)
    private LocalDate checkOutDate;
    
    private LocalDateTime actualCheckInTime;
    
    private LocalDateTime actualCheckOutTime;
    
    @Min(value = 1, message = "Số khách phải ít nhất là 1")
    @Column(nullable = false)
    private Integer numberOfGuests = 1;
    
    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.PENDING;
    
    @Column(nullable = false)
    private BigDecimal totalAmount = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private BigDecimal paidAmount = BigDecimal.ZERO;
    
    @Column(length = 1000)
    private String specialRequests;
    
    @Column(length = 1000)
    private String notes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (bookingCode == null) {
            bookingCode = generateBookingCode();
        }
        calculateTotalAmount();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String generateBookingCode() {
        return "BK" + System.currentTimeMillis();
    }
    
    public void calculateTotalAmount() {
        if (checkInDate != null && checkOutDate != null && room != null) {
            long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            if (nights < 1) nights = 1;
            totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        }
    }
    
    public BigDecimal getRemainingAmount() {
        return totalAmount.subtract(paidAmount);
    }
    
    public boolean isFullyPaid() {
        return paidAmount.compareTo(totalAmount) >= 0;
    }
    
    public enum BookingStatus {
        PENDING("Chờ xác nhận"),
        CONFIRMED("Đã xác nhận"),
        CHECKED_IN("Đã nhận phòng"),
        CHECKED_OUT("Đã trả phòng"),
        CANCELLED("Đã hủy"),
        NO_SHOW("Không đến");
        
        private final String displayName;
        
        BookingStatus(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

