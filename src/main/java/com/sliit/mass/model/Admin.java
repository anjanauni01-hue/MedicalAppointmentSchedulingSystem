package com.sliit.mass.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Admin (INHERITANCE: extends Person)
 * ------------------------------------------------
 * Represents the system administrator who manages doctors, patients and
 * oversees appointments. Overrides getRole() -> "ADMIN" (POLYMORPHISM).
 */
@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends Person {

    @Column(nullable = false)
    private String designation;

    public Admin() {
        super();
    }

    public Admin(String fullName, String email, String phoneNumber, String address, String designation) {
        super(fullName, email, phoneNumber, address);
        this.designation = designation;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
