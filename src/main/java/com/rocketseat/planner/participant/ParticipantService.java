package com.rocketseat.planner.participant;

import com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantService {

    private final ParticipantRepository repository;

    @Autowired
    public ParticipantService(ParticipantRepository repository){
        this.repository = repository;
    }

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();
        repository.saveAll(participants);
    }

    public Optional<Participant> getById(UUID id){
        return repository.findById(id);
    }

    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip){
        Participant newParticipant = repository.save(new Participant(email, trip));
        return new ParticipantCreateResponse(newParticipant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}

    public void triggerConfirmationEmailToParticipant(String email) {}

    public List<ParticipantData> getAllParticipantsFromEvent(UUID tripId) {
        return repository.findByTripId(tripId).stream().map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }

    public Participant setIsConfirmedTrue(Participant rawParticipant, String name){
        rawParticipant.setName(name);
        rawParticipant.setIsConfirmed(true);
        return repository.save(rawParticipant);
    }
}
