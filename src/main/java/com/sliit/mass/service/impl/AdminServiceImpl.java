package com.sliit.mass.service.impl;

import com.sliit.mass.model.Admin;
import com.sliit.mass.repository.AdminRepository;
import com.sliit.mass.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin registerAdmin(Admin admin) {
        if (admin.getFullName() == null || admin.getFullName().isBlank()) {
            throw new IllegalArgumentException("Admin name cannot be empty");
        }
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin not found with id: " + id));
    }

    @Override
    public Admin updateAdmin(Long id, Admin updated) {
        Admin existing = getAdminById(id);
        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setAddress(updated.getAddress());
        existing.setDesignation(updated.getDesignation());
        return adminRepository.save(existing);
    }

    @Override
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new NoSuchElementException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
}
