package com.example.HotelManage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.HotelService;
import com.example.HotelManage.repository.HotelServiceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceService {
    
    private final HotelServiceRepository hotelServiceRepository;
    
    public List<HotelService> getAllServices() {
        return hotelServiceRepository.findAll();
    }
    
    public HotelService getServiceById(Long id) {
        return hotelServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dịch vụ với ID: " + id));
    }
    
    public List<HotelService> getServicesByType(HotelService.ServiceType type) {
        return hotelServiceRepository.findByServiceType(type);
    }
    
    public List<HotelService> getAvailableServices() {
        return hotelServiceRepository.findByAvailable(true);
    }
    
    public List<HotelService> getAvailableServicesByType(HotelService.ServiceType type) {
        return hotelServiceRepository.findByServiceTypeAndAvailable(type, true);
    }
    
    public HotelService createService(HotelService service) {
        return hotelServiceRepository.save(service);
    }
    
    public HotelService updateService(Long id, HotelService serviceDetails) {
        HotelService service = getServiceById(id);
        
        service.setServiceName(serviceDetails.getServiceName());
        service.setServiceType(serviceDetails.getServiceType());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        service.setUnit(serviceDetails.getUnit());
        service.setAvailable(serviceDetails.getAvailable());
        service.setImageUrl(serviceDetails.getImageUrl());
        
        return hotelServiceRepository.save(service);
    }
    
    public void deleteService(Long id) {
        HotelService service = getServiceById(id);
        hotelServiceRepository.delete(service);
    }
    
    public void toggleServiceAvailability(Long id) {
        HotelService service = getServiceById(id);
        service.setAvailable(!service.getAvailable());
        hotelServiceRepository.save(service);
    }
}

