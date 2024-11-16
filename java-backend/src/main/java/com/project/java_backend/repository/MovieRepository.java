package com.project.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.java_backend.model.Movie;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // Custom method to find only public movies - to display to unregistered users
    List<Movie> findByIsPublicTrue();
    // Find only non-public movies for RUs
    List<Movie> findByIsPublicFalse();
}