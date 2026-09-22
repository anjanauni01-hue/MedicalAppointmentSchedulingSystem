package com.sliit.mass.service;

import com.sliit.mass.model.Patient;

import java.util.List;

/**
 * ABSTRACTION: the controller only ever talks to this interface, never to
 * PatientServiceImpl directly. The controller does not need to know HOW
 * a patient is validated or saved - only WHAT operations are available.
 * This is also INFORMATION HIDING at the architecture level.
 */
public interface PatientService {
    Patient registerPatient(Patient patient);      // Create
    List<Patient> getAllPatients();                 // Read (all)
    Patient getPatientById(Long id);                 // Read (one)
    Patient updatePatient(Long id, Patient updated); // Update
    void deletePatient(Long id);                     // Delete
    List<Patient> searchPatientsByName(String name);
}
