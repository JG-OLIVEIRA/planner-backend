package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityEntity, String> {
    List<ActivityEntity> findByTripId(String tripId);
}
