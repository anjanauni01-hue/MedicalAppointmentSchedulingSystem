package com.sliit.mass.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * InPersonAppointment (INHERITANCE: extends Appointment)
 * A physical, at-the-clinic visit. Adds a room number and charges a
 * small facility fee on top of the doctor's base consultation fee.
 */
@Entity
@Table(name = "inperson_appointments")
@DiscriminatorValue("IN_PERSON")
public class InPersonAppointment extends Appointment {

    private static final double FACILITY_CHARGE = 500.0;

    @Column(name = "room_number")
    private String roomNumber;

    public InPersonAppointment() {
        super();
    }

    public InPersonAppointment(Patient patient, Doctor doctor, LocalDate appointmentDate,
                                LocalTime appointmentTime, String reasonForVisit, String roomNumber) {
        super(patient, doctor, appointmentDate, appointmentTime, reasonForVisit);
        this.roomNumber = roomNumber;
    }

    @Override
    public String getModeDetails() {
        return "In-Person Visit - Room " + (roomNumber == null || roomNumber.isBlank() ? "TBA" : roomNumber);
    }

    @Override
    public double calculateFee(double baseConsultationFee) {
        // In-person visits add a fixed facility charge (private constant -> information hiding)
        return baseConsultationFee + FACILITY_CHARGE;
    }

    @Override
    public String getAppointmentType() {
        return "IN_PERSON";
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
