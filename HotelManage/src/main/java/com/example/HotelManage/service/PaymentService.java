package com.example.HotelManage.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.Booking;
import com.example.HotelManage.model.Payment;
import com.example.HotelManage.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán với ID: " + id));
    }
    
    public Payment getPaymentByCode(String paymentCode) {
        return paymentRepository.findByPaymentCode(paymentCode)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thanh toán với mã: " + paymentCode));
    }
    
    public List<Payment> getPaymentsByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }
    
    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
    
    public List<Payment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByDateRange(startDate, endDate);
    }
    
    public Payment createPayment(Payment payment) {
        Booking booking = payment.getBooking();
        
        // Validate payment amount
        BigDecimal remainingAmount = booking.getRemainingAmount();
        if (payment.getAmount().compareTo(remainingAmount) > 0) {
            throw new RuntimeException("Số tiền thanh toán vượt quá số tiền còn lại: " + remainingAmount + " VND");
        }
        
        // Save payment
        Payment savedPayment = paymentRepository.save(payment);
        
        // Update booking paid amount
        if (payment.getStatus() == Payment.PaymentStatus.COMPLETED) {
            bookingService.addPayment(booking.getId(), payment.getAmount());
        }
        
        return savedPayment;
    }
    
    public void confirmPayment(Long id) {
        Payment payment = getPaymentById(id);
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
        
        // Update booking paid amount
        bookingService.addPayment(payment.getBooking().getId(), payment.getAmount());
    }
    
    public void refundPayment(Long id, String reason) {
        Payment payment = getPaymentById(id);
        payment.setStatus(Payment.PaymentStatus.REFUNDED);
        payment.setNotes((payment.getNotes() != null ? payment.getNotes() + "\n" : "") + 
                        "Hoàn tiền: " + reason);
        paymentRepository.save(payment);
        
        // Update booking paid amount
        Booking booking = payment.getBooking();
        booking.setPaidAmount(booking.getPaidAmount().subtract(payment.getAmount()));
        bookingService.updateBooking(booking.getId(), booking);
    }
    
    public void deletePayment(Long id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }
    
    public BigDecimal calculateTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal revenue = paymentRepository.calculateTotalRevenue(startDate, endDate);
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
    
    public BigDecimal calculateTodayRevenue() {
        BigDecimal revenue = paymentRepository.calculateTodayRevenue();
        return revenue != null ? revenue : BigDecimal.ZERO;
    }
}

