package com.sliit.mass.controller;

import com.sliit.mass.repository.AppointmentRepository;
import com.sliit.mass.repository.DoctorRepository;
import com.sliit.mass.repository.PatientRepository;
import com.sliit.mass.model.AppointmentStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public HomeController(PatientRepository patientRepository, DoctorRepository doctorRepository,
                           AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalPatients", patientRepository.count());
        model.addAttribute("totalDoctors", doctorRepository.count());
        model.addAttribute("totalAppointments", appointmentRepository.count());
        model.addAttribute("pendingAppointments", appointmentRepository.findByStatus(AppointmentStatus.PENDING).size());
        return "index";
    }
}
