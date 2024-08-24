package com.rocketseat.planner.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, String> {
    List<Participant> findByTripId(String tripId);
}
