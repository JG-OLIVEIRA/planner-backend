package com.rocketseat.planner.controller;

import com.rocketseat.planner.model.Participant;
import com.rocketseat.planner.request.ParticipantRequestPayload;
import com.rocketseat.planner.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable String id, @RequestBody ParticipantRequestPayload payload){
        return participantService.getById(id)
                .map(participant -> {
                    Participant updatedParticipant = participantService.setIsConfirmedTrue(participant, payload.name());
                    return ResponseEntity.ok(updatedParticipant);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
