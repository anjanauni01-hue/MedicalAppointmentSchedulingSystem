package com.sliit.mass.service.impl;

import com.sliit.mass.model.Patient;
import com.sliit.mass.repository.PatientRepository;
import com.sliit.mass.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Concrete implementation of PatientService.
 * The controller never sees this class directly - it is injected through
 * the PatientService interface (ABSTRACTION). The private helper method
 * below (validate) is an example of INFORMATION HIDING: outside classes
 * cannot call it and don't need to know validation happens at all.
 */
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient registerPatient(Patient patient) {
        validate(patient);
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + id));
    }

    @Override
    public Patient updatePatient(Long id, Patient updated) {
        Patient existing = getPatientById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setAddress(updated.getAddress());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setGender(updated.getGender());
        existing.setBloodGroup(updated.getBloodGroup());
        existing.setMedicalHistory(updated.getMedicalHistory());
        validate(existing);
        return patientRepository.save(existing);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByFullNameContainingIgnoreCase(name);
    }

    /** Private helper - INFORMATION HIDING: internal validation logic is not exposed. */
    private void validate(Patient patient) {
        if (patient.getFullName() == null || patient.getFullName().isBlank()) {
            throw new IllegalArgumentException("Patient name cannot be empty");
        }
        if (patient.getEmail() == null || !patient.getEmail().contains("@")) {
            throw new IllegalArgumentException("A valid email is required");
        }
    }
}
