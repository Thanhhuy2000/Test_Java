package com.example.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.hotel.entity.User;
import com.example.hotel.repository.UserRepository;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return login();
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user, Model model) {
        try {
            if(userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "email đã được sử dụng");
            return "register";
        }
        
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setRole("CUSTOMER");

            userRepository.save(user);
            
            return "redirect:/login?success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Lỗi hệ thống " + e.getMessage());
            return "register";
        }
    }
}
