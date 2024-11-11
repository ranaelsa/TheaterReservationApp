package com.project.java_backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.java_backend.model.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

}
