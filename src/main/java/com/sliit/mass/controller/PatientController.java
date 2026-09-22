package com.sliit.mass.controller;

import com.sliit.mass.model.Patient;
import com.sliit.mass.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles all HTTP requests for the Patient module. This controller
 * implements the "any 3 of CRUD" requirement in full (Create, Read,
 * Update, Delete) plus a search (extra Read) - all delegated to
 * PatientService (the interface), never touching the repository or
 * database directly.
 */
@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // READ (all) + search
    @GetMapping
    public String listPatients(@RequestParam(value = "q", required = false) String query, Model model) {
        if (query != null && !query.isBlank()) {
            model.addAttribute("patients", patientService.searchPatientsByName(query));
            model.addAttribute("query", query);
        } else {
            model.addAttribute("patients", patientService.getAllPatients());
        }
        return "patients/list";
    }

    // Show CREATE form
    @GetMapping("/new")
    public String newPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("formTitle", "Register New Patient");
        return "patients/form";
    }

    // CREATE
    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient, RedirectAttributes redirectAttributes) {
        try {
            patientService.registerPatient(patient);
            redirectAttributes.addFlashAttribute("success", "Patient registered successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patients";
    }

    // Show UPDATE form
    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("formTitle", "Update Patient");
        return "patients/form";
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient,
                                 RedirectAttributes redirectAttributes) {
        try {
            patientService.updatePatient(id, patient);
            redirectAttributes.addFlashAttribute("success", "Patient updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patients";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            patientService.deletePatient(id);
            redirectAttributes.addFlashAttribute("success", "Patient deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/patients";
    }

    // READ (one) - view profile
    @GetMapping("/view/{id}")
    public String viewPatient(@PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        return "patients/view";
    }
}
