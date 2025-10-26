package com.example.HotelManage.controller.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HotelManage.model.Booking;
import com.example.HotelManage.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingRestController {
    
    private final BookingService bookingService;
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Booking> getBookingByCode(@PathVariable String code) {
        return ResponseEntity.ok(bookingService.getBookingByCode(code));
    }
    
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<Booking>> getBookingsByGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(bookingService.getBookingsByGuest(guestId));
    }
    
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Booking>> getBookingsByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(bookingService.getBookingsByRoom(roomId));
    }
    
    @GetMapping("/today-checkins")
    public ResponseEntity<List<Booking>> getTodayCheckIns() {
        return ResponseEntity.ok(bookingService.getTodayCheckIns());
    }
    
    @GetMapping("/today-checkouts")
    public ResponseEntity<List<Booking>> getTodayCheckOuts() {
        return ResponseEntity.ok(bookingService.getTodayCheckOuts());
    }
    
    @GetMapping("/currently-occupied")
    public ResponseEntity<List<Booking>> getCurrentlyOccupied() {
        return ResponseEntity.ok(bookingService.getCurrentlyOccupied());
    }
    
    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(booking));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }
    
    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirmBooking(@PathVariable Long id) {
        bookingService.confirmBooking(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/checkin")
    public ResponseEntity<Void> checkIn(@PathVariable Long id) {
        bookingService.checkIn(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/checkout")
    public ResponseEntity<Void> checkOut(@PathVariable Long id) {
        bookingService.checkOut(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id, @RequestParam String reason) {
        bookingService.cancelBooking(id, reason);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}

