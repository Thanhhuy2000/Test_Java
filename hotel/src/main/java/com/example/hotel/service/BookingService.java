package com.example.hotel.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.User;
import com.example.hotel.repository.BookingRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public String createBooking(Booking booking) {
        if(booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
            return "Thời gian đi phải sau thời gian đến";
        }

        List<Booking> conflict = bookingRepository.findConflictingBookings(booking.getRoom().getId(), booking.getCheckInDate(), booking.getCheckOutDate());
        if(!conflict.isEmpty()) {
            return "Không còn phòng trống";
        }

        long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if(days < 1) days = 1;

        double price = (booking.getRoom().getPrice() != null) ? booking.getRoom().getPrice() : 0;

        booking.setTotalPrice(days * price);
        booking.setStatus("PENDING");

        bookingRepository.save(booking);

        return "SUCCESS";
    }

    public void cancelBooking(Long bookingId, User currentUser) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if(booking != null) {
            if(booking.getUser().getId().equals(currentUser.getId()) && booking.getStatus().equals("PENDING")) {
                booking.setStatus("CANCELLED");
                bookingRepository.save(booking);
            }
        }
    }
}
