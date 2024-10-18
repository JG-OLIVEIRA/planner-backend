package com.rocketseat.planner.controller;

import com.rocketseat.planner.model.ParticipantEntity;
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
    public ResponseEntity<ParticipantEntity> confirmParticipant(@PathVariable String id, @RequestBody ParticipantRequestPayload payload){
        return participantService.getById(id)
                .map(participantEntity -> {
                    ParticipantEntity updatedParticipantEntity = participantService.setIsConfirmedTrue(participantEntity, payload.name());
                    return ResponseEntity.ok(updatedParticipantEntity);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
