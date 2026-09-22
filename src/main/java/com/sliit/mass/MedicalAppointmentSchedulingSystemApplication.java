package com.sliit.mass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SE1020 - Object Oriented Programming
 * Project: Medical Appointment Scheduling System
 *
 * Entry point of the Spring Boot application.
 *
 * Author: Anjana
 * Student ID: IT22195470
 */
@SpringBootApplication
public class MedicalAppointmentSchedulingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalAppointmentSchedulingSystemApplication.class, args);
        System.out.println("========================================================");
        System.out.println(" Medical Appointment Scheduling System started");
        System.out.println(" Open your browser at: http://localhost:8080");
        System.out.println("========================================================");
    }
}
