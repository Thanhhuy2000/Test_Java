package com.example.HotelManage.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HotelManage.model.Booking;
import com.example.HotelManage.model.Guest;
import com.example.HotelManage.model.Room;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    Optional<Booking> findByBookingCode(String bookingCode);
    
    List<Booking> findByGuest(Guest guest);
    
    List<Booking> findByRoom(Room room);
    
    List<Booking> findByStatus(Booking.BookingStatus status);
    
    List<Booking> findByGuestId(Long guestId);
    
    List<Booking> findByRoomId(Long roomId);
    
    @Query("SELECT b FROM Booking b WHERE b.checkInDate = :date AND b.status IN ('CONFIRMED', 'PENDING')")
    List<Booking> findCheckInsForDate(@Param("date") LocalDate date);
    
    @Query("SELECT b FROM Booking b WHERE b.checkOutDate = :date AND b.status = 'CHECKED_IN'")
    List<Booking> findCheckOutsForDate(@Param("date") LocalDate date);
    
    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
           "AND b.status IN ('CONFIRMED', 'CHECKED_IN') " +
           "AND ((b.checkInDate <= :checkOut) AND (b.checkOutDate >= :checkIn))")
    List<Booking> findConflictingBookings(@Param("roomId") Long roomId, 
                                          @Param("checkIn") LocalDate checkIn, 
                                          @Param("checkOut") LocalDate checkOut);
    
    @Query("SELECT b FROM Booking b WHERE b.status = 'CHECKED_IN'")
    List<Booking> findCurrentlyOccupiedBookings();
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status IN ('CONFIRMED', 'CHECKED_IN') " +
           "AND b.checkInDate <= :date AND b.checkOutDate >= :date")
    Long countOccupiedRoomsOnDate(@Param("date") LocalDate date);
}

