package com.project.java_backend.service;

import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.repository.RegisteredUserRepository;
import com.project.java_backend.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class RegisteredUserService {

    @Autowired
    private RegisteredUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users
    public List<RegisteredUser> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public RegisteredUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    // Create new user
    public RegisteredUser createUser(RegisteredUser user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Update existing user
    public RegisteredUser updateUser(Long id, RegisteredUser userDetails) {
        RegisteredUser user = getUserById(id);

        if(userDetails.getName() != null && !userDetails.getName().isBlank()){user.setName(userDetails.getName());}
        if(userDetails.getAddress() != null && !userDetails.getAddress().isBlank()){user.setAddress(userDetails.getAddress());}
        if(userDetails.getCardNumber() != null && !userDetails.getCardNumber().isBlank()){user.setCardNumber(userDetails.getCardNumber());}
        if(userDetails.getExpiryDate() != null && !userDetails.getExpiryDate().isBlank()){user.setExpiryDate(userDetails.getExpiryDate());}
        if(userDetails.getCvc() != null && !userDetails.getCvc().isBlank()){user.setCvc(userDetails.getCvc());}
        // If updating password
        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    // Retrieve a Registered User by email (useful for login/authentication)
    public Optional<RegisteredUser> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    // Authenticate user
    public RegisteredUser authenticate(String email, String password) {
        RegisteredUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
