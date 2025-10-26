package com.example.HotelManage.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.Booking;
import com.example.HotelManage.model.Room;
import com.example.HotelManage.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final GuestService guestService;
    
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với ID: " + id));
    }
    
    public Booking getBookingByCode(String bookingCode) {
        return bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking với mã: " + bookingCode));
    }
    
    public List<Booking> getBookingsByGuest(Long guestId) {
        return bookingRepository.findByGuestId(guestId);
    }
    
    public List<Booking> getBookingsByRoom(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }
    
    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }
    
    public List<Booking> getTodayCheckIns() {
        return bookingRepository.findCheckInsForDate(LocalDate.now());
    }
    
    public List<Booking> getTodayCheckOuts() {
        return bookingRepository.findCheckOutsForDate(LocalDate.now());
    }
    
    public List<Booking> getCurrentlyOccupied() {
        return bookingRepository.findCurrentlyOccupiedBookings();
    }
    
    public Booking createBooking(Booking booking) {
        // Validate dates
        if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
            throw new RuntimeException("Ngày check-out phải sau ngày check-in");
        }
        
        // Check room availability
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
            booking.getRoom().getId(),
            booking.getCheckInDate(),
            booking.getCheckOutDate()
        );
        
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Phòng đã được đặt trong khoảng thời gian này");
        }
        
        // Calculate total amount
        booking.calculateTotalAmount();
        
        // Save booking
        Booking savedBooking = bookingRepository.save(booking);
        
        // Update room status if confirmed
        if (booking.getStatus() == Booking.BookingStatus.CONFIRMED) {
            roomService.updateRoomStatus(booking.getRoom().getId(), Room.RoomStatus.RESERVED);
        }
        
        return savedBooking;
    }
    
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = getBookingById(id);
        
        booking.setCheckInDate(bookingDetails.getCheckInDate());
        booking.setCheckOutDate(bookingDetails.getCheckOutDate());
        booking.setNumberOfGuests(bookingDetails.getNumberOfGuests());
        booking.setSpecialRequests(bookingDetails.getSpecialRequests());
        booking.setNotes(bookingDetails.getNotes());
        
        booking.calculateTotalAmount();
        
        return bookingRepository.save(booking);
    }
    
    public void confirmBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        roomService.updateRoomStatus(booking.getRoom().getId(), Room.RoomStatus.RESERVED);
        bookingRepository.save(booking);
    }
    
    public void checkIn(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(Booking.BookingStatus.CHECKED_IN);
        booking.setActualCheckInTime(LocalDateTime.now());
        roomService.updateRoomStatus(booking.getRoom().getId(), Room.RoomStatus.OCCUPIED);
        bookingRepository.save(booking);
    }
    
    public void checkOut(Long id) {
        Booking booking = getBookingById(id);
        
        if (!booking.isFullyPaid()) {
            throw new RuntimeException("Khách hàng chưa thanh toán đủ. Còn thiếu: " + 
                                     booking.getRemainingAmount() + " VND");
        }
        
        booking.setStatus(Booking.BookingStatus.CHECKED_OUT);
        booking.setActualCheckOutTime(LocalDateTime.now());
        roomService.updateRoomStatus(booking.getRoom().getId(), Room.RoomStatus.CLEANING);
        bookingRepository.save(booking);
    }
    
    public void cancelBooking(Long id, String reason) {
        Booking booking = getBookingById(id);
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.setNotes((booking.getNotes() != null ? booking.getNotes() + "\n" : "") + 
                        "Đã hủy: " + reason);
        
        if (booking.getRoom().getStatus() == Room.RoomStatus.RESERVED) {
            roomService.updateRoomStatus(booking.getRoom().getId(), Room.RoomStatus.AVAILABLE);
        }
        
        bookingRepository.save(booking);
    }
    
    public void addPayment(Long bookingId, BigDecimal amount) {
        Booking booking = getBookingById(bookingId);
        booking.setPaidAmount(booking.getPaidAmount().add(amount));
        bookingRepository.save(booking);
    }
    
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
    
    public long countTotalBookings() {
        return bookingRepository.count();
    }
    
    public long countOccupiedRoomsToday() {
        return bookingRepository.countOccupiedRoomsOnDate(LocalDate.now());
    }
}

