package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, String> {
}
