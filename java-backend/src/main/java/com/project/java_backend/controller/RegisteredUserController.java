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
    private RegisteredUserService userService;

    // Get all users
    @GetMapping
    public List<RegisteredUser> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<RegisteredUser> getUserById(@PathVariable Long id) {
        RegisteredUser user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Register a new user
    @PostMapping(value="/register", consumes="application/json", produces="application/json")
    public ResponseEntity<RegisteredUser> registerUser(@RequestBody RegisteredUser user) {
        System.out.println(user);
        RegisteredUser createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Update user
    @PutMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<RegisteredUser> updateUser(@PathVariable Long id, @RequestBody RegisteredUser userDetails) {
        RegisteredUser updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete user
    @DeleteMapping(value="/{id}", consumes="application/json", produces="application/json")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Authenticate user
    @PostMapping(value="/login", consumes="application/json", produces="application/json")
    public ResponseEntity<RegisteredUser> authenticateUser(@RequestBody RegisteredUser loginRequest) {
        RegisteredUser authenticatedUser = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(authenticatedUser);
    }
}
