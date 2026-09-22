package com.sliit.mass.service.impl;

import com.sliit.mass.model.Doctor;
import com.sliit.mass.repository.DoctorRepository;
import com.sliit.mass.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor registerDoctor(Doctor doctor) {
        validate(doctor);
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Doctor not found with id: " + id));
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor updated) {
        Doctor existing = getDoctorById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setAddress(updated.getAddress());
        existing.setSpecialization(updated.getSpecialization());
        existing.setLicenseNumber(updated.getLicenseNumber());
        existing.setConsultationFee(updated.getConsultationFee());
        existing.setAvailableDays(updated.getAvailableDays());
        validate(existing);
        return doctorRepository.save(existing);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new NoSuchElementException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> searchDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }

    private void validate(Doctor doctor) {
        if (doctor.getFullName() == null || doctor.getFullName().isBlank()) {
            throw new IllegalArgumentException("Doctor name cannot be empty");
        }
        if (doctor.getConsultationFee() < 0) {
            throw new IllegalArgumentException("Consultation fee cannot be negative");
        }
    }
}
