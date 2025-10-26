package com.example.HotelManage.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.Room;
import com.example.HotelManage.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
    
    private final RoomRepository roomRepository;
    
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với ID: " + id));
    }
    
    public Room getRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng số: " + roomNumber));
    }
    
    public List<Room> getRoomsByStatus(Room.RoomStatus status) {
        return roomRepository.findByStatus(status);
    }
    
    public List<Room> getRoomsByType(Room.RoomType roomType) {
        return roomRepository.findByRoomType(roomType);
    }
    
    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus(Room.RoomStatus.AVAILABLE);
    }
    
    public List<Room> getAvailableRoomsForPeriod(LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRooms(checkIn, checkOut);
    }
    
    public Room createRoom(Room room) {
        if (roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            throw new RuntimeException("Số phòng " + room.getRoomNumber() + " đã tồn tại");
        }
        return roomRepository.save(room);
    }
    
    public Room updateRoom(Long id, Room roomDetails) {
        Room room = getRoomById(id);
        
        if (!room.getRoomNumber().equals(roomDetails.getRoomNumber()) && 
            roomRepository.existsByRoomNumber(roomDetails.getRoomNumber())) {
            throw new RuntimeException("Số phòng " + roomDetails.getRoomNumber() + " đã tồn tại");
        }
        
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setRoomType(roomDetails.getRoomType());
        room.setPricePerNight(roomDetails.getPricePerNight());
        room.setStatus(roomDetails.getStatus());
        room.setMaxOccupancy(roomDetails.getMaxOccupancy());
        room.setFloor(roomDetails.getFloor());
        room.setDescription(roomDetails.getDescription());
        room.setAmenities(roomDetails.getAmenities());
        
        return roomRepository.save(room);
    }
    
    public void updateRoomStatus(Long id, Room.RoomStatus status) {
        Room room = getRoomById(id);
        room.setStatus(status);
        roomRepository.save(room);
    }
    
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
    
    public long countTotalRooms() {
        return roomRepository.count();
    }
    
    public long countAvailableRooms() {
        return roomRepository.findByStatus(Room.RoomStatus.AVAILABLE).size();
    }
}

