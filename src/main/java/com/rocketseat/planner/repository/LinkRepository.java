package com.rocketseat.planner.repository;

import java.util.List;

import com.rocketseat.planner.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, String>{
    List<Link> findByTripId(String tripId);
}