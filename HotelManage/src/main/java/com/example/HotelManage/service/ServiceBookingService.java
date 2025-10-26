package com.example.HotelManage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.ServiceBooking;
import com.example.HotelManage.repository.ServiceBookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceBookingService {
    
    private final ServiceBookingRepository serviceBookingRepository;
    
    public List<ServiceBooking> getAllServiceBookings() {
        return serviceBookingRepository.findAll();
    }
    
    public ServiceBooking getServiceBookingById(Long id) {
        return serviceBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đặt dịch vụ với ID: " + id));
    }
    
    public List<ServiceBooking> getServiceBookingsByBookingId(Long bookingId) {
        return serviceBookingRepository.findByBookingId(bookingId);
    }
    
    public List<ServiceBooking> getServiceBookingsByGuestId(Long guestId) {
        return serviceBookingRepository.findByGuestId(guestId);
    }
    
    public List<ServiceBooking> getServiceBookingsByStatus(ServiceBooking.ServiceBookingStatus status) {
        return serviceBookingRepository.findByStatus(status);
    }
    
    public ServiceBooking createServiceBooking(ServiceBooking serviceBooking) {
        serviceBooking.calculateTotal();
        return serviceBookingRepository.save(serviceBooking);
    }
    
    public ServiceBooking updateServiceBooking(Long id, ServiceBooking serviceBookingDetails) {
        ServiceBooking serviceBooking = getServiceBookingById(id);
        
        serviceBooking.setQuantity(serviceBookingDetails.getQuantity());
        serviceBooking.setServiceTime(serviceBookingDetails.getServiceTime());
        serviceBooking.setStatus(serviceBookingDetails.getStatus());
        serviceBooking.setNotes(serviceBookingDetails.getNotes());
        serviceBooking.calculateTotal();
        
        return serviceBookingRepository.save(serviceBooking);
    }
    
    public void confirmServiceBooking(Long id) {
        ServiceBooking serviceBooking = getServiceBookingById(id);
        serviceBooking.setStatus(ServiceBooking.ServiceBookingStatus.CONFIRMED);
        serviceBookingRepository.save(serviceBooking);
    }
    
    public void completeServiceBooking(Long id) {
        ServiceBooking serviceBooking = getServiceBookingById(id);
        serviceBooking.setStatus(ServiceBooking.ServiceBookingStatus.COMPLETED);
        serviceBookingRepository.save(serviceBooking);
    }
    
    public void cancelServiceBooking(Long id) {
        ServiceBooking serviceBooking = getServiceBookingById(id);
        serviceBooking.setStatus(ServiceBooking.ServiceBookingStatus.CANCELLED);
        serviceBookingRepository.save(serviceBooking);
    }
    
    public void deleteServiceBooking(Long id) {
        ServiceBooking serviceBooking = getServiceBookingById(id);
        serviceBookingRepository.delete(serviceBooking);
    }
}

