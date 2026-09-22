package com.sliit.mass.controller;

import com.sliit.mass.model.Admin;
import com.sliit.mass.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String listAdmins(Model model) {
        model.addAttribute("admins", adminService.getAllAdmins());
        return "admins/list";
    }

    @GetMapping("/new")
    public String newAdminForm(Model model) {
        model.addAttribute("admin", new Admin());
        model.addAttribute("formTitle", "Register New Admin");
        return "admins/form";
    }

    @PostMapping("/save")
    public String saveAdmin(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {
        try {
            adminService.registerAdmin(admin);
            redirectAttributes.addFlashAttribute("success", "Admin registered successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String editAdminForm(@PathVariable Long id, Model model) {
        model.addAttribute("admin", adminService.getAdminById(id));
        model.addAttribute("formTitle", "Update Admin");
        return "admins/form";
    }

    @PostMapping("/update/{id}")
    public String updateAdmin(@PathVariable Long id, @ModelAttribute Admin admin,
                               RedirectAttributes redirectAttributes) {
        try {
            adminService.updateAdmin(id, admin);
            redirectAttributes.addFlashAttribute("success", "Admin updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admins";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            adminService.deleteAdmin(id);
            redirectAttributes.addFlashAttribute("success", "Admin deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admins";
    }
}
