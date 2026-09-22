package com.sliit.mass.service;

import com.sliit.mass.model.Admin;

import java.util.List;

public interface AdminService {
    Admin registerAdmin(Admin admin);         // Create
    List<Admin> getAllAdmins();                // Read (all)
    Admin getAdminById(Long id);                // Read (one)
    Admin updateAdmin(Long id, Admin updated);  // Update
    void deleteAdmin(Long id);                  // Delete
}
