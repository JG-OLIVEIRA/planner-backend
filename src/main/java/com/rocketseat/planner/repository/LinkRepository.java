package com.rocketseat.planner.repository;

import java.util.List;

import com.rocketseat.planner.model.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<LinkEntity, String>{
    List<LinkEntity> findByTripId(String tripId);
}