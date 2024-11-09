package com.project.java_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
     List<Seat> findByTheaterId( Long theaterId );
}