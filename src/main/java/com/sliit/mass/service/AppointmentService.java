package com.sliit.mass.service;

import com.sliit.mass.model.Appointment;
import com.sliit.mass.model.AppointmentStatus;

import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(Appointment appointment);              // Create
    List<Appointment> getAllAppointments();                             // Read (all)
    Appointment getAppointmentById(Long id);                             // Read (one)
    Appointment updateAppointment(Long id, Appointment updated);         // Update (full)
    Appointment updateStatus(Long id, AppointmentStatus newStatus);      // Update (status only)
    void deleteAppointment(Long id);                                     // Delete
    List<Appointment> getAppointmentsByPatient(Long patientId);
    List<Appointment> getAppointmentsByDoctor(Long doctorId);
    double calculateAppointmentFee(Long appointmentId);
}
