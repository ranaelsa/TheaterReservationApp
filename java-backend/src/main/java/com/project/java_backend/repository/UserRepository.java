package com.project.java_backend.repository;

import org.springframework.stereotype.Repository;

import com.project.java_backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Define any custom query methods here
}
