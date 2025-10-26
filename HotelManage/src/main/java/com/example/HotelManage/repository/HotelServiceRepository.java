package com.example.HotelManage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HotelManage.model.HotelService;

@Repository
public interface HotelServiceRepository extends JpaRepository<HotelService, Long> {
    
    List<HotelService> findByServiceType(HotelService.ServiceType serviceType);
    
    List<HotelService> findByAvailable(Boolean available);
    
    List<HotelService> findByServiceTypeAndAvailable(HotelService.ServiceType serviceType, Boolean available);
}

