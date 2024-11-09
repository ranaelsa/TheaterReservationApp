package com.project.java_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.SeatAvailability;
import com.project.java_backend.model.SeatAvailabilityId;

public interface SeatAvailabilityRepository extends JpaRepository<SeatAvailability, Long> {
    List<SeatAvailability> findByShowtimeIdAndIsAvailableTrue(Long showtimeId);

    Optional<SeatAvailability> findById(SeatAvailabilityId id);
}