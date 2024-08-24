package com.rocketseat.planner.link;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, String>{
    List<Link> findByTripId(String tripId);
}