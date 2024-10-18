package com.rocketseat.planner.service;

import com.rocketseat.planner.model.ParticipantEntity;
import com.rocketseat.planner.response.ParticipantResponse;
import com.rocketseat.planner.response.ParticipantDataResponse;
import com.rocketseat.planner.repository.ParticipantRepository;
import com.rocketseat.planner.model.TripEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipantService {

    private final ParticipantRepository repository;

    @Autowired
    public ParticipantService(ParticipantRepository repository){
        this.repository = repository;
    }

    public void registerParticipantsToEvent(List<String> participantsToInvite, TripEntity tripEntity){
        List<ParticipantEntity> participantEntities = participantsToInvite.stream().map(email -> new ParticipantEntity(email, tripEntity)).toList();
        repository.saveAll(participantEntities);
    }

    public Optional<ParticipantEntity> getById(String id){
        return repository.findById(id);
    }

    public ParticipantResponse registerParticipantToEvent(String email, TripEntity tripEntity){
        ParticipantEntity newParticipantEntity = repository.save(new ParticipantEntity(email, tripEntity));
        return new ParticipantResponse(newParticipantEntity.getId());
    }

    public void triggerConfirmationEmailToParticipants(String tripId){}

    public void triggerConfirmationEmailToParticipant(String email) {}

    public List<ParticipantDataResponse> getAllParticipantsFromEvent(String tripId) {
        return repository.findByTripId(tripId).stream().map(participantEntity -> new ParticipantDataResponse(participantEntity.getId(), participantEntity.getName(), participantEntity.getEmail(), participantEntity.getIsConfirmed())).toList();
    }

    public ParticipantEntity setIsConfirmedTrue(ParticipantEntity rawParticipantEntity, String name){
        rawParticipantEntity.setName(name);
        rawParticipantEntity.setIsConfirmed(true);
        return repository.save(rawParticipantEntity);
    }
}
