package com.rocketseat.planner.service;

import com.rocketseat.planner.model.Participant;
import com.rocketseat.planner.response.ParticipantResponse;
import com.rocketseat.planner.response.ParticipantDataResponse;
import com.rocketseat.planner.repository.ParticipantRepository;
import com.rocketseat.planner.model.Trip;
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

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participantEntities = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        repository.saveAll(participantEntities);
    }

    public Optional<Participant> getById(String id){
        return repository.findById(id);
    }

    public ParticipantResponse registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant = repository.save(new Participant(email, trip));
        return new ParticipantResponse(newParticipant.getId());
    }

    public void triggerConfirmationEmailToParticipants(String tripId){}

    public void triggerConfirmationEmailToParticipant(String email) {}

    public List<ParticipantDataResponse> getAllParticipantsFromEvent(String tripId) {
        return repository.findByTripId(tripId).stream().map(participant -> new ParticipantDataResponse(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }

    public Participant setIsConfirmedTrue(Participant rawParticipant, String name){
        rawParticipant.setName(name);
        rawParticipant.setIsConfirmed(true);
        return repository.save(rawParticipant);
    }
}
