package com.example.HotelManage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HotelManage.model.Booking;
import com.example.HotelManage.service.BookingService;
import com.example.HotelManage.service.GuestService;
import com.example.HotelManage.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;
    private final RoomService roomService;
    private final GuestService guestService;
    
    @GetMapping
    public String listBookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("bookingStatuses", Booking.BookingStatus.values());
        return "bookings/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("rooms", roomService.getAvailableRooms());
        model.addAttribute("guests", guestService.getAllGuests());
        model.addAttribute("bookingStatuses", Booking.BookingStatus.values());
        return "bookings/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("guests", guestService.getAllGuests());
        model.addAttribute("bookingStatuses", Booking.BookingStatus.values());
        return "bookings/form";
    }
    
    @GetMapping("/{id}")
    public String viewBooking(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        return "bookings/view";
    }
    
    @PostMapping("/save")
    public String saveBooking(@Valid @ModelAttribute("booking") Booking booking, 
                             BindingResult result, 
                             @RequestParam Long guestId,
                             @RequestParam Long roomId,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("guests", guestService.getAllGuests());
            model.addAttribute("bookingStatuses", Booking.BookingStatus.values());
            return "bookings/form";
        }
        
        try {
            booking.setGuest(guestService.getGuestById(guestId));
            booking.setRoom(roomService.getRoomById(roomId));
            
            if (booking.getId() == null) {
                bookingService.createBooking(booking);
                redirectAttributes.addFlashAttribute("success", "Tạo booking thành công!");
            } else {
                bookingService.updateBooking(booking.getId(), booking);
                redirectAttributes.addFlashAttribute("success", "Cập nhật booking thành công!");
            }
            return "redirect:/bookings";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("guests", guestService.getAllGuests());
            model.addAttribute("bookingStatuses", Booking.BookingStatus.values());
            return "bookings/form";
        }
    }
    
    @GetMapping("/{id}/confirm")
    public String confirmBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.confirmBooking(id);
            redirectAttributes.addFlashAttribute("success", "Xác nhận booking thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/" + id;
    }
    
    @GetMapping("/{id}/checkin")
    public String checkIn(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.checkIn(id);
            redirectAttributes.addFlashAttribute("success", "Check-in thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/" + id;
    }
    
    @GetMapping("/{id}/checkout")
    public String checkOut(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.checkOut(id);
            redirectAttributes.addFlashAttribute("success", "Check-out thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/" + id;
    }
    
    @GetMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, 
                               @RequestParam String reason,
                               RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(id, reason);
            redirectAttributes.addFlashAttribute("success", "Hủy booking thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/" + id;
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.deleteBooking(id);
            redirectAttributes.addFlashAttribute("success", "Xóa booking thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa booking: " + e.getMessage());
        }
        return "redirect:/bookings";
    }
}

