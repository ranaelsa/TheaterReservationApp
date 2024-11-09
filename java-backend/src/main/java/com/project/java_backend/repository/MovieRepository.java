package com.project.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}