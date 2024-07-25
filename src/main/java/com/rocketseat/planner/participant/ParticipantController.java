package com.rocketseat.planner.participant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload){
        return participantService.getById(id)
                .map(participant -> {
                    Participant updatedParticipant = participantService.setIsConfirmedTrue(participant, payload.name());
                    return ResponseEntity.ok(updatedParticipant);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
