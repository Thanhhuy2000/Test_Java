package com.example.HotelManage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HotelManage.model.Room;
import com.example.HotelManage.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {
    
    private final RoomService roomService;
    
    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("roomTypes", Room.RoomType.values());
        model.addAttribute("roomStatuses", Room.RoomStatus.values());
        return "rooms/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("roomTypes", Room.RoomType.values());
        model.addAttribute("roomStatuses", Room.RoomStatus.values());
        return "rooms/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        model.addAttribute("roomTypes", Room.RoomType.values());
        model.addAttribute("roomStatuses", Room.RoomStatus.values());
        return "rooms/form";
    }
    
    @GetMapping("/{id}")
    public String viewRoom(@PathVariable Long id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id));
        return "rooms/view";
    }
    
    @PostMapping("/save")
    public String saveRoom(@Valid @ModelAttribute("room") Room room, 
                          BindingResult result, 
                          RedirectAttributes redirectAttributes,
                          Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roomTypes", Room.RoomType.values());
            model.addAttribute("roomStatuses", Room.RoomStatus.values());
            return "rooms/form";
        }
        
        try {
            if (room.getId() == null) {
                roomService.createRoom(room);
                redirectAttributes.addFlashAttribute("success", "Tạo phòng thành công!");
            } else {
                roomService.updateRoom(room.getId(), room);
                redirectAttributes.addFlashAttribute("success", "Cập nhật phòng thành công!");
            }
            return "redirect:/rooms";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("roomTypes", Room.RoomType.values());
            model.addAttribute("roomStatuses", Room.RoomStatus.values());
            return "rooms/form";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addFlashAttribute("success", "Xóa phòng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa phòng: " + e.getMessage());
        }
        return "redirect:/rooms";
    }
}

