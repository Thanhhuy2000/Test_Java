package com.example.hotel.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Import quan trọng
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // Import quan trọng
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.hotel.entity.Room;
import com.example.hotel.service.RoomService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        return "admin/room-list";
    }

    @GetMapping("/rooms/create")
    public String createForm(Model model) {
        model.addAttribute("room", new Room());
        return "admin/room-form";
    }

    @PostMapping("/rooms/save")
    public String saveRoom(@ModelAttribute Room room, 
                           @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        
        if (!imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            
            Path uploadPath = Paths.get("src/main/resources/static/images");
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, imageFile.getBytes());
            
            room.setImage("/images/" + fileName);
        }
        
        roomService.saveRoom(room);
        return "redirect:/admin/rooms";
    }
}
