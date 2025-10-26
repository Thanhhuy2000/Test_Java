package com.example.HotelManage.controller.web;

import java.time.LocalDateTime;

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

import com.example.HotelManage.model.Payment;
import com.example.HotelManage.service.BookingService;
import com.example.HotelManage.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;
    private final BookingService bookingService;
    
    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
        model.addAttribute("paymentStatuses", Payment.PaymentStatus.values());
        return "payments/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(@RequestParam(required = false) Long bookingId, Model model) {
        Payment payment = new Payment();
        if (bookingId != null) {
            payment.setBooking(bookingService.getBookingById(bookingId));
            payment.setPaymentDate(LocalDateTime.now());
        }
        model.addAttribute("payment", payment);
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
        model.addAttribute("paymentStatuses", Payment.PaymentStatus.values());
        return "payments/form";
    }
    
    @GetMapping("/{id}")
    public String viewPayment(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.getPaymentById(id));
        return "payments/view";
    }
    
    @PostMapping("/save")
    public String savePayment(@Valid @ModelAttribute("payment") Payment payment, 
                             BindingResult result, 
                             @RequestParam Long bookingId,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bookings", bookingService.getAllBookings());
            model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
            model.addAttribute("paymentStatuses", Payment.PaymentStatus.values());
            return "payments/form";
        }
        
        try {
            payment.setBooking(bookingService.getBookingById(bookingId));
            paymentService.createPayment(payment);
            redirectAttributes.addFlashAttribute("success", "Tạo thanh toán thành công!");
            return "redirect:/payments";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("bookings", bookingService.getAllBookings());
            model.addAttribute("paymentMethods", Payment.PaymentMethod.values());
            model.addAttribute("paymentStatuses", Payment.PaymentStatus.values());
            return "payments/form";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            paymentService.deletePayment(id);
            redirectAttributes.addFlashAttribute("success", "Xóa thanh toán thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa thanh toán: " + e.getMessage());
        }
        return "redirect:/payments";
    }
}

