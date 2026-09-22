package com.sliit.mass.service;

import com.sliit.mass.model.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor registerDoctor(Doctor doctor);           // Create
    List<Doctor> getAllDoctors();                    // Read (all)
    Doctor getDoctorById(Long id);                    // Read (one)
    Doctor updateDoctor(Long id, Doctor updated);     // Update
    void deleteDoctor(Long id);                       // Delete
    List<Doctor> searchDoctorsBySpecialization(String specialization);
}
