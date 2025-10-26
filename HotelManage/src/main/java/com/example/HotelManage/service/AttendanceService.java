package com.example.HotelManage.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.HotelManage.model.Attendance;
import com.example.HotelManage.model.User;
import com.example.HotelManage.repository.AttendanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
    
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chấm công với ID: " + id));
    }
    
    public List<Attendance> getAttendanceByUserId(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }
    
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
    
    public List<Attendance> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateRange(startDate, endDate);
    }
    
    public List<Attendance> getAttendanceByUserAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByUserIdAndDateRange(userId, startDate, endDate);
    }
    
    public Attendance checkIn(Long userId) {
        LocalDate today = LocalDate.now();
        
        // Kiểm tra đã check-in chưa
        var existing = attendanceRepository.findByUserIdAndDate(userId, today);
        if (existing.isPresent()) {
            throw new RuntimeException("Bạn đã check-in hôm nay");
        }
        
        User user = userService.getUserById(userId);
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalTime.now());
        
        // Xác định trạng thái (đi muộn nếu sau 8:00)
        if (LocalTime.now().isAfter(LocalTime.of(8, 15))) {
            attendance.setStatus(Attendance.AttendanceStatus.LATE);
        } else {
            attendance.setStatus(Attendance.AttendanceStatus.PRESENT);
        }
        
        return attendanceRepository.save(attendance);
    }
    
    public Attendance checkOut(Long userId) {
        LocalDate today = LocalDate.now();
        
        Attendance attendance = attendanceRepository.findByUserIdAndDate(userId, today)
                .orElseThrow(() -> new RuntimeException("Bạn chưa check-in hôm nay"));
        
        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Bạn đã check-out rồi");
        }
        
        attendance.setCheckOutTime(LocalTime.now());
        attendance.calculateWorkingHours();
        
        return attendanceRepository.save(attendance);
    }
    
    public Attendance createAttendance(Attendance attendance) {
        // Kiểm tra trùng lặp
        var existing = attendanceRepository.findByUserIdAndDate(
            attendance.getUser().getId(), 
            attendance.getDate()
        );
        
        if (existing.isPresent()) {
            throw new RuntimeException("Đã có bản ghi chấm công cho nhân viên này trong ngày");
        }
        
        return attendanceRepository.save(attendance);
    }
    
    public Attendance updateAttendance(Long id, Attendance attendanceDetails) {
        Attendance attendance = getAttendanceById(id);
        
        attendance.setDate(attendanceDetails.getDate());
        attendance.setCheckInTime(attendanceDetails.getCheckInTime());
        attendance.setCheckOutTime(attendanceDetails.getCheckOutTime());
        attendance.setStatus(attendanceDetails.getStatus());
        attendance.setNotes(attendanceDetails.getNotes());
        attendance.calculateWorkingHours();
        
        return attendanceRepository.save(attendance);
    }
    
    public void deleteAttendance(Long id) {
        Attendance attendance = getAttendanceById(id);
        attendanceRepository.delete(attendance);
    }
    
    public Double getTotalWorkingHours(Long userId, LocalDate startDate, LocalDate endDate) {
        Long minutes = attendanceRepository.getTotalWorkingMinutes(userId, startDate, endDate);
        return minutes != null ? minutes / 60.0 : 0.0;
    }
}

