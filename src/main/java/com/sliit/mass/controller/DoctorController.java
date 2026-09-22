package com.sliit.mass.controller;

import com.sliit.mass.model.Doctor;
import com.sliit.mass.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(@RequestParam(value = "q", required = false) String query, Model model) {
        if (query != null && !query.isBlank()) {
            model.addAttribute("doctors", doctorService.searchDoctorsBySpecialization(query));
            model.addAttribute("query", query);
        } else {
            model.addAttribute("doctors", doctorService.getAllDoctors());
        }
        return "doctors/list";
    }

    @GetMapping("/new")
    public String newDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("formTitle", "Register New Doctor");
        return "doctors/form";
    }

    @PostMapping("/save")
    public String saveDoctor(@ModelAttribute Doctor doctor, RedirectAttributes redirectAttributes) {
        try {
            doctorService.registerDoctor(doctor);
            redirectAttributes.addFlashAttribute("success", "Doctor registered successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editDoctorForm(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        model.addAttribute("formTitle", "Update Doctor");
        return "doctors/form";
    }

    @PostMapping("/update/{id}")
    public String updateDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor,
                                RedirectAttributes redirectAttributes) {
        try {
            doctorService.updateDoctor(id, doctor);
            redirectAttributes.addFlashAttribute("success", "Doctor updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            doctorService.deleteDoctor(id);
            redirectAttributes.addFlashAttribute("success", "Doctor deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/doctors";
    }

    @GetMapping("/view/{id}")
    public String viewDoctor(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.getDoctorById(id));
        return "doctors/view";
    }
}
