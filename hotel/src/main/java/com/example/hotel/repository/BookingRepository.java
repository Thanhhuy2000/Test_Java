package com.example.hotel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
           "AND b.status <> 'CANCELLED' " +
           "AND ((b.checkInDate < :checkOut) AND (b.checkOutDate > :checkIn))")
    List<Booking> findConflictingBookings(
            @Param("roomId") Long roomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut);
}
