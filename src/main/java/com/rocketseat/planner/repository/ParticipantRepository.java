package com.rocketseat.planner.repository;

import com.rocketseat.planner.model.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, String> {
    List<ParticipantEntity> findByTripId(String tripId);
}
