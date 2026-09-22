package com.sliit.mass.controller;

import com.sliit.mass.model.*;
import com.sliit.mass.service.AppointmentService;
import com.sliit.mass.service.DoctorService;
import com.sliit.mass.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentController(AppointmentService appointmentService, PatientService patientService,
                                  DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    // READ (all)
    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/list";
    }

    // Show CREATE (book) form
    @GetMapping("/new")
    public String newAppointmentForm(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "appointments/form";
    }

    // CREATE - builds either an InPersonAppointment or OnlineAppointment
    // depending on the "appointmentType" radio button chosen in the form.
    // This is where POLYMORPHISM is created: the same "book" action
    // produces two different concrete object types.
    @PostMapping("/save")
    public String bookAppointment(@RequestParam Long patientId,
                                   @RequestParam Long doctorId,
                                   @RequestParam String appointmentDate,
                                   @RequestParam String appointmentTime,
                                   @RequestParam(required = false) String reasonForVisit,
                                   @RequestParam String appointmentType,
                                   @RequestParam(required = false) String roomNumber,
                                   @RequestParam(required = false) String meetingLink,
                                   RedirectAttributes redirectAttributes) {
        try {
            Patient patient = patientService.getPatientById(patientId);
            Doctor doctor = doctorService.getDoctorById(doctorId);
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);

            Appointment appointment;
            if ("ONLINE".equalsIgnoreCase(appointmentType)) {
                appointment = new OnlineAppointment(patient, doctor, date, time, reasonForVisit, meetingLink);
            } else {
                appointment = new InPersonAppointment(patient, doctor, date, time, reasonForVisit, roomNumber);
            }

            appointmentService.bookAppointment(appointment);
            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/appointments";
    }

    // UPDATE status (Confirm / Complete / Cancel)
    @GetMapping("/status/{id}/{status}")
    public String updateStatus(@PathVariable Long id, @PathVariable String status,
                                RedirectAttributes redirectAttributes) {
        try {
            AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());
            appointmentService.updateStatus(id, newStatus);
            redirectAttributes.addFlashAttribute("success", "Appointment status updated to " + newStatus + ".");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/appointments";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            appointmentService.deleteAppointment(id);
            redirectAttributes.addFlashAttribute("success", "Appointment deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/appointments";
    }

    // READ (one) - shows details + the polymorphic fee calculation result
    @GetMapping("/view/{id}")
    public String viewAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        model.addAttribute("calculatedFee", appointmentService.calculateAppointmentFee(id));
        return "appointments/view";
    }
}
