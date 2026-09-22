package com.sliit.mass.model;

/**
 * Enum representing the lifecycle of an Appointment.
 * Using an enum (instead of a raw String) prevents invalid status values
 * from ever being stored - another small form of information hiding /
 * data integrity.
 */
public enum AppointmentStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELLED
}
