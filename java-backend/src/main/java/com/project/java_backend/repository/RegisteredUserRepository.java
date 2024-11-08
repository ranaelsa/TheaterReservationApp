package com.project.java_backend.repository;

import org.springframework.stereotype.Repository;

import com.project.java_backend.model.RegisteredUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, Long> {
    // Define any custom query methods here
    Optional<RegisteredUser> findByEmail(String email);
    
    Optional<RegisteredUser> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

}
