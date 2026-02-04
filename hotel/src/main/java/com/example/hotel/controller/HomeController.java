package com.example.hotel.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Room;
import com.example.hotel.service.RoomService;

@Controller
public class HomeController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String home (@RequestParam(required = false) LocalDate checkIn,
                        @RequestParam(required = false) LocalDate checkOut,
                        Model model) {
        if(checkIn != null && checkOut != null) {
            model.addAttribute("Rooms", roomService.getAvailableRooms(checkIn, checkOut));
            model.addAttribute("message", "Co phong tu " + checkIn + " den " + checkOut);
        }
        else {
            model.addAttribute("rooms", roomService.getAllRooms());
        }
        
        return "home";
    }

    @GetMapping("/room/{id}")
    public String roomDetail(@PathVariable Long id, Model model) {
        Room room = roomService.getRoomById(id);
        if(room == null) {
            return "/";
        }
        model.addAttribute("room", room);
        model.addAttribute("newBooking", new Booking());

        return "room-detail";
    }
}
