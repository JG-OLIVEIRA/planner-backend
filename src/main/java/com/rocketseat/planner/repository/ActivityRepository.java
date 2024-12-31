package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    List<Activity> findByTripId(String tripId);
}
