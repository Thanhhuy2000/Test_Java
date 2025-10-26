package com.example.HotelManage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.Guest;
import com.example.HotelManage.repository.GuestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {
    
    private final GuestRepository guestRepository;
    
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }
    
    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với ID: " + id));
    }
    
    public Guest getGuestByIdentityCard(String identityCard) {
        return guestRepository.findByIdentityCard(identityCard)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng với CMND/CCCD: " + identityCard));
    }
    
    public List<Guest> searchGuestsByName(String name) {
        return guestRepository.searchByName(name);
    }
    
    public List<Guest> searchGuestsByKeyword(String keyword) {
        return guestRepository.searchByKeyword(keyword);
    }
    
    public Guest createGuest(Guest guest) {
        if (guestRepository.existsByIdentityCard(guest.getIdentityCard())) {
            throw new RuntimeException("Khách hàng với CMND/CCCD " + guest.getIdentityCard() + " đã tồn tại");
        }
        return guestRepository.save(guest);
    }
    
    public Guest updateGuest(Long id, Guest guestDetails) {
        Guest guest = getGuestById(id);
        
        if (!guest.getIdentityCard().equals(guestDetails.getIdentityCard()) && 
            guestRepository.existsByIdentityCard(guestDetails.getIdentityCard())) {
            throw new RuntimeException("CMND/CCCD " + guestDetails.getIdentityCard() + " đã được sử dụng");
        }
        
        guest.setFullName(guestDetails.getFullName());
        guest.setEmail(guestDetails.getEmail());
        guest.setPhone(guestDetails.getPhone());
        guest.setIdentityCard(guestDetails.getIdentityCard());
        guest.setDateOfBirth(guestDetails.getDateOfBirth());
        guest.setGender(guestDetails.getGender());
        guest.setAddress(guestDetails.getAddress());
        guest.setNationality(guestDetails.getNationality());
        guest.setNotes(guestDetails.getNotes());
        
        return guestRepository.save(guest);
    }
    
    public void deleteGuest(Long id) {
        Guest guest = getGuestById(id);
        guestRepository.delete(guest);
    }
    
    public long countTotalGuests() {
        return guestRepository.count();
    }
}

