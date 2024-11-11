package com.project.java_backend.repository;

import org.springframework.stereotype.Repository;

import com.project.java_backend.model.RegisteredUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    // Find by email
    Optional<RegisteredUser> findByEmail(String email);
    
    // Find by email and password
    Optional<RegisteredUser> findByEmailAndPassword(String email, String password);

    // Find if email exists
    boolean existsByEmail(String email);

}
