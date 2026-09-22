package com.sliit.mass.repository;

import com.sliit.mass.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByFullNameContainingIgnoreCase(String name);
    List<Doctor> findBySpecializationContainingIgnoreCase(String specialization);
}
