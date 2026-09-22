package com.sliit.mass.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient (INHERITANCE: extends Person)
 * ------------------------------------------------
 * A Patient IS-A Person, so it inherits fullName, email, phoneNumber,
 * address, id, createdAt from Person and simply adds the fields that are
 * specific to a patient (date of birth, blood group, medical history).
 *
 * Overrides getRole() -> POLYMORPHISM: calling getRole() on a Patient
 * object returns "PATIENT", while the same call on a Doctor object
 * returns "DOCTOR" - one method signature, different behaviour.
 */
@Entity
@Table(name = "patients")
@DiscriminatorValue("PATIENT")
public class Patient extends Person {

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String gender;

    @Column(name = "blood_group")
    private String bloodGroup;

    @Column(name = "medical_history", length = 1000)
    private String medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    public Patient() {
        super();
    }

    public Patient(String fullName, String email, String phoneNumber, String address,
                    LocalDate dateOfBirth, String gender, String bloodGroup, String medicalHistory) {
        super(fullName, email, phoneNumber, address);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String getRole() {
        return "PATIENT";
    }

    // ---------------- Encapsulated getters / setters ----------------

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
