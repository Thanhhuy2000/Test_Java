package com.example.HotelManage.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HotelManage.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
    Optional<Room> findByRoomNumber(String roomNumber);
    
    List<Room> findByStatus(Room.RoomStatus status);
    
    List<Room> findByRoomType(Room.RoomType roomType);
    
    List<Room> findByFloor(Integer floor);
    
    List<Room> findByRoomTypeAndStatus(Room.RoomType roomType, Room.RoomStatus status);
    
    @Query("SELECT r FROM Room r WHERE r.status = 'AVAILABLE' AND r.id NOT IN " +
           "(SELECT b.room.id FROM Booking b WHERE b.status IN ('CONFIRMED', 'CHECKED_IN') " +
           "AND ((b.checkInDate <= :checkOut) AND (b.checkOutDate >= :checkIn)))")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);
    
    boolean existsByRoomNumber(String roomNumber);
}

