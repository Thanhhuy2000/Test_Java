package com.example.HotelManage.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HotelManage.model.ServiceBooking;

@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    
    List<ServiceBooking> findByBookingId(Long bookingId);
    
    List<ServiceBooking> findByServiceId(Long serviceId);
    
    List<ServiceBooking> findByStatus(ServiceBooking.ServiceBookingStatus status);
    
    @Query("SELECT sb FROM ServiceBooking sb WHERE sb.serviceTime BETWEEN :startDate AND :endDate")
    List<ServiceBooking> findByServiceTimeRange(@Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT sb FROM ServiceBooking sb WHERE sb.booking.guest.id = :guestId")
    List<ServiceBooking> findByGuestId(@Param("guestId") Long guestId);
}

