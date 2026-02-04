package com.example.hotel.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.hotel.entity.Room;
import com.example.hotel.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void saveRoom(Room room) {
        if(room != null) {
            roomRepository.save(room);
        }
    }

    public Room getRoomById(Long id) {
        if(id == null) {
            return null;
        }
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getAvailableRooms(@Param("checkIn") LocalDate checkIn,
                                        @Param("checkOut") LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }
}
