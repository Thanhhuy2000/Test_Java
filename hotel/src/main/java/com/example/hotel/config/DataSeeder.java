package com.example.hotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.hotel.entity.User;
import com.example.hotel.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("brainio.learning@gmail.com") == null) {
            
            User admin = new User();
            admin.setEmail("brainio.learning@gmail.com");
            admin.setFullName("Quản Trị Viên Hệ Thống");
            
            admin.setPassword(passwordEncoder.encode("Brainio@12345"));
            
            admin.setRole("ROLE_MANAGER");
            
            admin.setEnabled(true); 
            
            userRepository.save(admin);
            
            System.out.println("------ ĐÃ TẠO TÀI KHOẢN ADMIN MẶC ĐỊNH ------");
            System.out.println("Email: brainio.learning@gmail.com");
            System.out.println("Pass : Brainio@12345");
            System.out.println("---------------------------------------------");
        }
    }
}
