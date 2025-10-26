package com.example.HotelManage.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.HotelManage.model.Guest;
import com.example.HotelManage.service.GuestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {
    
    private final GuestService guestService;
    
    @GetMapping
    public String listGuests(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("guests", guestService.searchGuestsByKeyword(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("guests", guestService.getAllGuests());
        }
        model.addAttribute("genders", Guest.Gender.values());
        return "guests/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("guest", new Guest());
        model.addAttribute("genders", Guest.Gender.values());
        return "guests/form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("guest", guestService.getGuestById(id));
        model.addAttribute("genders", Guest.Gender.values());
        return "guests/form";
    }
    
    @GetMapping("/{id}")
    public String viewGuest(@PathVariable Long id, Model model) {
        model.addAttribute("guest", guestService.getGuestById(id));
        return "guests/view";
    }
    
    @PostMapping("/save")
    public String saveGuest(@Valid @ModelAttribute("guest") Guest guest, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("genders", Guest.Gender.values());
            return "guests/form";
        }
        
        try {
            if (guest.getId() == null) {
                guestService.createGuest(guest);
                redirectAttributes.addFlashAttribute("success", "Tạo khách hàng thành công!");
            } else {
                guestService.updateGuest(guest.getId(), guest);
                redirectAttributes.addFlashAttribute("success", "Cập nhật khách hàng thành công!");
            }
            return "redirect:/guests";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("genders", Guest.Gender.values());
            return "guests/form";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            guestService.deleteGuest(id);
            redirectAttributes.addFlashAttribute("success", "Xóa khách hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa khách hàng: " + e.getMessage());
        }
        return "redirect:/guests";
    }
}

