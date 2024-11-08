package com.project.java_backend.service;

import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisteredUserService {

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    // Get all users
    public List<RegisteredUser> getAllUsers() {
        return registeredUserRepository.findAll();
    }

    // Get user by ID
    public Optional<RegisteredUser> getUserById(Long id) {
        return registeredUserRepository.findById(id);
    }

    // Create new user
    public RegisteredUser createUser(RegisteredUser user) {
        if (registeredUserRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // TODO: Hash the password before saving
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        return registeredUserRepository.save(user);
    }

    // Update existing user
    public RegisteredUser updateUser(Long id, RegisteredUser userDetails) {
        RegisteredUser user = registeredUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword()); // Remember to hash the password
        user.setAddress(userDetails.getAddress());
        user.setCardNumber(userDetails.getCardNumber());
        user.setExpiryDate(userDetails.getExpiryDate());
        user.setCvc(userDetails.getCvc());

        return registeredUserRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        if (!registeredUserRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        registeredUserRepository.deleteById(id);
    }
}
