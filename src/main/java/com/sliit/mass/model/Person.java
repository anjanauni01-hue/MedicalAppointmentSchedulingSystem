package com.sliit.mass.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Person (ABSTRACTION + INHERITANCE base class)
 * ------------------------------------------------
 * This is the abstract superclass shared by every "person" in the system
 * (Patient, Doctor, Admin). It cannot be instantiated on its own - only
 * through one of its concrete subclasses. This demonstrates ABSTRACTION:
 * we model what every person has in common, and force subclasses to
 * define how a person's ROLE is described (getRole()).
 *
 * All fields are declared 'private' and only reachable through public
 * getters/setters -> ENCAPSULATION + INFORMATION HIDING. No other class
 * can directly touch a Person's internal state; they must go through the
 * controlled methods below (some of which perform validation).
 */
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type")
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phoneNumber;

    private String address;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    protected Person() {
        // Required by JPA
    }

    protected Person(String fullName, String email, String phoneNumber, String address) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * ABSTRACTION + POLYMORPHISM:
     * Every subclass MUST provide its own implementation of getRole().
     * When we call person.getRole() on a list of mixed Patients/Doctors/
     * Admins, Java calls the correct overridden version at RUNTIME
     * depending on the actual object type - this is runtime polymorphism.
     */
    public abstract String getRole();

    // ---------------- Encapsulated getters / setters ----------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return getRole() + "{id=" + id + ", fullName='" + fullName + "', email='" + email + "'}";
    }
}
