package com.sliit.mass.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor (INHERITANCE: extends Person)
 * ------------------------------------------------
 * Like Patient, a Doctor IS-A Person. It adds doctor-specific fields
 * (specialization, licenseNumber, consultationFee, availableDays) and
 * overrides getRole() to return "DOCTOR" (POLYMORPHISM).
 */
@Entity
@Table(name = "doctors")
@DiscriminatorValue("DOCTOR")
public class Doctor extends Person {

    @Column(nullable = false)
    private String specialization;

    @Column(name = "license_number", unique = true)
    private String licenseNumber;

    @Column(name = "consultation_fee")
    private double consultationFee;

    /** Comma separated e.g. "Monday,Wednesday,Friday" */
    @Column(name = "available_days")
    private String availableDays;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor() {
        super();
    }

    public Doctor(String fullName, String email, String phoneNumber, String address,
                   String specialization, String licenseNumber, double consultationFee, String availableDays) {
        super(fullName, email, phoneNumber, address);
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.consultationFee = consultationFee;
        this.availableDays = availableDays;
    }

    @Override
    public String getRole() {
        return "DOCTOR";
    }

    // ---------------- Encapsulated getters / setters ----------------

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(String availableDays) {
        this.availableDays = availableDays;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
