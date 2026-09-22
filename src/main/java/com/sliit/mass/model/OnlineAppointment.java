package com.sliit.mass.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * OnlineAppointment (INHERITANCE: extends Appointment)
 * A remote video-call consultation. Adds a meeting link and gives a
 * small discount on the doctor's base consultation fee since no
 * physical room is used.
 */
@Entity
@Table(name = "online_appointments")
@DiscriminatorValue("ONLINE")
public class OnlineAppointment extends Appointment {

    private static final double ONLINE_DISCOUNT_RATE = 0.10; // 10% off

    @Column(name = "meeting_link")
    private String meetingLink;

    public OnlineAppointment() {
        super();
    }

    public OnlineAppointment(Patient patient, Doctor doctor, LocalDate appointmentDate,
                              LocalTime appointmentTime, String reasonForVisit, String meetingLink) {
        super(patient, doctor, appointmentDate, appointmentTime, reasonForVisit);
        this.meetingLink = meetingLink;
    }

    @Override
    public String getModeDetails() {
        return "Online Video Call - " + (meetingLink == null || meetingLink.isBlank() ? "link generated after confirmation" : meetingLink);
    }

    @Override
    public double calculateFee(double baseConsultationFee) {
        // Online visits get a discount since no clinic room is used
        return baseConsultationFee * (1 - ONLINE_DISCOUNT_RATE);
    }

    @Override
    public String getAppointmentType() {
        return "ONLINE";
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }
}
