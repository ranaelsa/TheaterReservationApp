package com.project.java_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.Showtime;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    List<Showtime> findByTheaterId(Long theaterId);
    List<Showtime> findByMovieId(Long movieId);
}