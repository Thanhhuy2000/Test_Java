package com.example.hotel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.User;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.service.BookingService;
import com.example.hotel.service.RoomService;

@Controller
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
     private UserRepository userRepository;

    @PostMapping("/booking/submit")
    public String submitBooking(@ModelAttribute Booking booking, 
                                @RequestParam("roomId") Long roomId, 
                                Principal principal, 
                                Model model) {
        if (principal == null) return "redirect:/login";

        User user = userRepository.findByEmail(principal.getName());
        Room room = roomService.getRoomById(roomId);

        booking.setUser(user);
        booking.setRoom(room);

        String result = bookingService.createBooking(booking);

        if ("SUCCESS".equals(result)) {
            return "redirect:/my-bookings";
        } else {
            model.addAttribute("error", result);
            model.addAttribute("room", room);
            return "room-detail";
        }
    }

    @GetMapping("/my-bookings")
    public String myBookings(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        
        User user = userRepository.findByEmail(principal.getName());
        model.addAttribute("bookings", bookingService.getBookingsByUser(user));
        
        return "my-bookings";
    }

    @GetMapping("/booking/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        bookingService.cancelBooking(id, user);
        return "redirect:/my-bookings";
    }
}
