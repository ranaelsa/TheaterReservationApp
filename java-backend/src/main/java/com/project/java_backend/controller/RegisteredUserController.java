package com.project.java_backend.controller;

import com.project.java_backend.model.RegisteredUser;
import com.project.java_backend.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<RegisteredUser>> getAllUsers() {
        List<RegisteredUser> users = registeredUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredUser> getUserById(@PathVariable Long id) {
        return registeredUserService.getUserById(id)
                .map(user -> {
                    // Mask sensitive data before sending response
                    user.setPassword(null);
                    user.setCardNumber(0);
                    user.setExpiryDate(0);
                    user.setCvc(0);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new user
    @PostMapping
    public ResponseEntity<RegisteredUser> createUser(@RequestBody RegisteredUser user) {
        try {
            RegisteredUser createdUser = registeredUserService.createUser(user);
            // Mask sensitive data
            createdUser.setPassword(null);
            createdUser.setCardNumber(0);
            createdUser.setExpiryDate(0);
            createdUser.setCvc(0);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Update existing user
    @PutMapping("/{id}")
    public ResponseEntity<RegisteredUser> updateUser(@PathVariable Long id, @RequestBody RegisteredUser userDetails) {
        try {
            RegisteredUser updatedUser = registeredUserService.updateUser(id, userDetails);
            // Mask sensitive data
            updatedUser.setPassword(null);
            updatedUser.setCardNumber(0);
            updatedUser.setExpiryDate(0);
            updatedUser.setCvc(0);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            registeredUserService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
