package com.example.HotelManage.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HotelManage.model.Booking;
import com.example.HotelManage.model.Guest;
import com.example.HotelManage.model.Room;
import com.example.HotelManage.model.User;
import com.example.HotelManage.repository.BookingRepository;
import com.example.HotelManage.repository.GuestRepository;
import com.example.HotelManage.repository.RoomRepository;
import com.example.HotelManage.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initDatabase(
            RoomRepository roomRepository,
            GuestRepository guestRepository,
            BookingRepository bookingRepository,
            UserService userService) {
        
        return args -> {
            if (roomRepository.count() == 0) {
                log.info("Khởi tạo dữ liệu mẫu...");
                
                // Tạo phòng mẫu
                Room room101 = new Room();
                room101.setRoomNumber("101");
                room101.setRoomType(Room.RoomType.SINGLE);
                room101.setPricePerNight(new BigDecimal("500000"));
                room101.setStatus(Room.RoomStatus.AVAILABLE);
                room101.setMaxOccupancy(2);
                room101.setFloor(1);
                room101.setDescription("Phòng đơn tiêu chuẩn với view thành phố");
                room101.setAmenities("WiFi, TV, AC, Mini-bar");
                roomRepository.save(room101);
                
                Room room102 = new Room();
                room102.setRoomNumber("102");
                room102.setRoomType(Room.RoomType.DOUBLE);
                room102.setPricePerNight(new BigDecimal("800000"));
                room102.setStatus(Room.RoomStatus.AVAILABLE);
                room102.setMaxOccupancy(4);
                room102.setFloor(1);
                room102.setDescription("Phòng đôi sang trọng");
                room102.setAmenities("WiFi, TV, AC, Mini-bar, Tủ lạnh");
                roomRepository.save(room102);
                
                Room room201 = new Room();
                room201.setRoomNumber("201");
                room201.setRoomType(Room.RoomType.SUITE);
                room201.setPricePerNight(new BigDecimal("1500000"));
                room201.setStatus(Room.RoomStatus.AVAILABLE);
                room201.setMaxOccupancy(4);
                room201.setFloor(2);
                room201.setDescription("Phòng Suite cao cấp với phòng khách riêng");
                room201.setAmenities("WiFi, Smart TV, AC, Mini-bar, Tủ lạnh, Bồn tắm");
                roomRepository.save(room201);
                
                Room room202 = new Room();
                room202.setRoomNumber("202");
                room202.setRoomType(Room.RoomType.DELUXE);
                room202.setPricePerNight(new BigDecimal("1200000"));
                room202.setStatus(Room.RoomStatus.AVAILABLE);
                room202.setMaxOccupancy(3);
                room202.setFloor(2);
                room202.setDescription("Phòng Deluxe với nội thất hiện đại");
                room202.setAmenities("WiFi, Smart TV, AC, Mini-bar, Tủ lạnh");
                roomRepository.save(room202);
                
                Room room301 = new Room();
                room301.setRoomNumber("301");
                room301.setRoomType(Room.RoomType.PRESIDENTIAL);
                room301.setPricePerNight(new BigDecimal("3000000"));
                room301.setStatus(Room.RoomStatus.AVAILABLE);
                room301.setMaxOccupancy(6);
                room301.setFloor(3);
                room301.setDescription("Phòng Tổng thống với đầy đủ tiện nghi cao cấp");
                room301.setAmenities("WiFi, Smart TV 4K, AC, Mini-bar, Tủ lạnh, Bồn tắm Jacuzzi, Phòng khách, Phòng ăn");
                roomRepository.save(room301);
                
                log.info("Đã tạo {} phòng", roomRepository.count());
                
                // Tạo khách hàng mẫu
                Guest guest1 = new Guest();
                guest1.setFullName("Nguyễn Văn An");
                guest1.setEmail("nguyenvanan@email.com");
                guest1.setPhone("0901234567");
                guest1.setIdentityCard("001234567890");
                guest1.setDateOfBirth(LocalDate.of(1990, 5, 15));
                guest1.setGender(Guest.Gender.MALE);
                guest1.setAddress("123 Đường ABC, Quận 1, TP.HCM");
                guest1.setNationality("Việt Nam");
                guestRepository.save(guest1);
                
                Guest guest2 = new Guest();
                guest2.setFullName("Trần Thị Bình");
                guest2.setEmail("tranthib@email.com");
                guest2.setPhone("0912345678");
                guest2.setIdentityCard("001234567891");
                guest2.setDateOfBirth(LocalDate.of(1985, 8, 20));
                guest2.setGender(Guest.Gender.FEMALE);
                guest2.setAddress("456 Đường XYZ, Quận 3, TP.HCM");
                guest2.setNationality("Việt Nam");
                guestRepository.save(guest2);
                
                Guest guest3 = new Guest();
                guest3.setFullName("Lê Hoàng Cường");
                guest3.setEmail("lehoangcuong@email.com");
                guest3.setPhone("0923456789");
                guest3.setIdentityCard("001234567892");
                guest3.setDateOfBirth(LocalDate.of(1992, 3, 10));
                guest3.setGender(Guest.Gender.MALE);
                guest3.setAddress("789 Đường DEF, Quận 5, TP.HCM");
                guest3.setNationality("Việt Nam");
                guestRepository.save(guest3);
                
                log.info("Đã tạo {} khách hàng", guestRepository.count());
                
                // Tạo booking mẫu
                Booking booking1 = new Booking();
                booking1.setGuest(guest1);
                booking1.setRoom(room101);
                booking1.setCheckInDate(LocalDate.now().plusDays(1));
                booking1.setCheckOutDate(LocalDate.now().plusDays(3));
                booking1.setNumberOfGuests(2);
                booking1.setStatus(Booking.BookingStatus.CONFIRMED);
                booking1.setSpecialRequests("Yêu cầu phòng tầng cao");
                bookingRepository.save(booking1);
                
                Booking booking2 = new Booking();
                booking2.setGuest(guest2);
                booking2.setRoom(room201);
                booking2.setCheckInDate(LocalDate.now());
                booking2.setCheckOutDate(LocalDate.now().plusDays(2));
                booking2.setNumberOfGuests(3);
                booking2.setStatus(Booking.BookingStatus.CHECKED_IN);
                bookingRepository.save(booking2);
                
                log.info("Đã tạo {} booking", bookingRepository.count());
                
                // Tạo user admin
                if (!userService.getAllUsers().isEmpty()) {
                    log.info("User admin đã tồn tại");
                } else {
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPassword("admin123");
                    admin.setFullName("Administrator");
                    admin.setEmail("admin@hotelmanage.com");
                    admin.setPhone("0900000000");
                    admin.setRole(User.UserRole.ADMIN);
                    admin.setActive(true);
                    userService.createUser(admin);
                    
                    User staff = new User();
                    staff.setUsername("staff");
                    staff.setPassword("staff123");
                    staff.setFullName("Nhân viên");
                    staff.setEmail("staff@hotelmanage.com");
                    staff.setPhone("0900000001");
                    staff.setRole(User.UserRole.STAFF);
                    staff.setActive(true);
                    userService.createUser(staff);
                    
                    log.info("Đã tạo user admin và staff");
                    log.info("Username: admin / Password: admin123");
                    log.info("Username: staff / Password: staff123");
                }
                
                log.info("Khởi tạo dữ liệu mẫu hoàn tất!");
            } else {
                log.info("Dữ liệu đã tồn tại, bỏ qua khởi tạo");
            }
        };
    }
}

