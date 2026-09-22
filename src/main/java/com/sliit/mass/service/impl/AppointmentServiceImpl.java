package com.sliit.mass.service.impl;

import com.sliit.mass.model.Appointment;
import com.sliit.mass.model.AppointmentStatus;
import com.sliit.mass.repository.AppointmentRepository;
import com.sliit.mass.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment bookAppointment(Appointment appointment) {
        validate(appointment);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found with id: " + id));
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = getAppointmentById(id);
        existing.setPatient(updated.getPatient());
        existing.setDoctor(updated.getDoctor());
        existing.setAppointmentDate(updated.getAppointmentDate());
        existing.setAppointmentTime(updated.getAppointmentTime());
        existing.setReasonForVisit(updated.getReasonForVisit());
        return appointmentRepository.save(existing);
    }

    @Override
    public Appointment updateStatus(Long id, AppointmentStatus newStatus) {
        Appointment existing = getAppointmentById(id);
        existing.setStatus(newStatus);
        return appointmentRepository.save(existing);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NoSuchElementException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    /**
     * POLYMORPHISM IN ACTION:
     * We don't know (and don't need to know) at compile time whether
     * 'appointment' is an InPersonAppointment or OnlineAppointment.
     * Calling calculateFee() here runs whichever overridden version
     * matches the ACTUAL object at runtime.
     */
    @Override
    public double calculateAppointmentFee(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        double baseFee = appointment.getDoctor().getConsultationFee();
        return appointment.calculateFee(baseFee);
    }

    private void validate(Appointment appointment) {
        if (appointment.getPatient() == null) {
            throw new IllegalArgumentException("An appointment must have a patient");
        }
        if (appointment.getDoctor() == null) {
            throw new IllegalArgumentException("An appointment must have a doctor");
        }
        if (appointment.getAppointmentDate() == null || appointment.getAppointmentTime() == null) {
            throw new IllegalArgumentException("Appointment date and time are required");
        }
    }
}
