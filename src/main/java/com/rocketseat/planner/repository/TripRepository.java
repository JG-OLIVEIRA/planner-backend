package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<TripEntity, String> {
}
