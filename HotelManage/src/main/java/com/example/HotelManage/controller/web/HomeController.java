package com.example.HotelManage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.HotelManage.service.BookingService;
import com.example.HotelManage.service.PaymentService;
import com.example.HotelManage.service.RoomService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    
    private final RoomService roomService;
    private final BookingService bookingService;
    private final PaymentService paymentService;
    
    @GetMapping("/")
    public String home(Model model) {
        // Dashboard statistics
        model.addAttribute("totalRooms", roomService.countTotalRooms());
        model.addAttribute("availableRooms", roomService.countAvailableRooms());
        model.addAttribute("occupiedRooms", bookingService.countOccupiedRoomsToday());
        model.addAttribute("todayRevenue", paymentService.calculateTodayRevenue());
        
        // Today's activities
        model.addAttribute("todayCheckIns", bookingService.getTodayCheckIns());
        model.addAttribute("todayCheckOuts", bookingService.getTodayCheckOuts());
        model.addAttribute("currentlyOccupied", bookingService.getCurrentlyOccupied());
        
        return "index";
    }
    
    @GetMapping("/home")
    public String homeAlias(Model model) {
        return home(model);
    }
}

