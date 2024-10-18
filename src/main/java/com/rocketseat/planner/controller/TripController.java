package com.rocketseat.planner.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.rocketseat.planner.model.TripEntity;
import com.rocketseat.planner.response.TripResponse;
import com.rocketseat.planner.request.TripRequestPayload;
import com.rocketseat.planner.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.planner.response.ActivityDataResponse;
import com.rocketseat.planner.request.ActivityRequestPayload;
import com.rocketseat.planner.response.ActivityResponse;
import com.rocketseat.planner.service.ActivityService;
import com.rocketseat.planner.response.LinkDataResponse;
import com.rocketseat.planner.request.LinkRequestPayload;
import com.rocketseat.planner.response.LinkResponse;
import com.rocketseat.planner.service.LinkService;
import com.rocketseat.planner.response.ParticipantResponse;
import com.rocketseat.planner.response.ParticipantDataResponse;
import com.rocketseat.planner.request.ParticipantRequestPayload;
import com.rocketseat.planner.service.ParticipantService;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    private final ActivityService activityService;

    private final LinkService linkService;

    private final ParticipantService participantService;

    public TripController(TripService tripService, ActivityService activityService, LinkService linkService, ParticipantService participantService){
        this.tripService = tripService;
        this.activityService = activityService;
        this.linkService = linkService;
        this.participantService = participantService;
    }

    //Trip
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripRequestPayload payload) {
        TripEntity newTripEntity = tripService.register(new TripEntity(payload));

        participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTripEntity);

        return ResponseEntity.ok(new TripResponse(newTripEntity.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripEntity> getTripById(@PathVariable String id){
        return tripService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripEntity> updateTrip(@PathVariable String id, @RequestBody TripRequestPayload payload){
        return tripService.getById(id)
                .map(trip -> {
                    LocalDateTime ends_at = LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDateTime starts_at = LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
                    String destination = payload.destination();

                    TripEntity updatedTripEntity = tripService.update(trip, ends_at, starts_at, destination);

                    return ResponseEntity.ok(updatedTripEntity);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<TripEntity> confirmTrip(@PathVariable String id){
        return tripService.getById(id)
                .map(trip -> {
                    participantService.triggerConfirmationEmailToParticipants(id);
                    TripEntity updatedTripEntity = tripService.setIsConfirmedTrue(trip);
                    return ResponseEntity.ok(updatedTripEntity);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Activity
    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> createActivity(@PathVariable String id, @RequestBody ActivityRequestPayload payload) {
        return tripService.getById(id)
                .map(trip -> {
                    ActivityResponse activityResponse = activityService.register(payload, trip);
                    return ResponseEntity.ok(activityResponse);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDataResponse>> getAllActivities(@PathVariable String id){
        List<ActivityDataResponse> activities = activityService.getAllActivitiesFromId(id);

        if(activities.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(activities);
    }

    //Participant
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDataResponse>> getAllParticipants(@PathVariable String id){
        List<ParticipantDataResponse> participants = participantService.getAllParticipantsFromEvent(id);

        if(participants.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantResponse> inviteParticipant(@PathVariable String id, @RequestBody ParticipantRequestPayload payload) {
        return tripService.getById(id)
                .map(trip -> {
                    ParticipantResponse participantResponse = participantService.registerParticipantToEvent(payload.email(), trip);

                    if(trip.getIsConfirmed()) {
                        participantService.triggerConfirmationEmailToParticipant(payload.email());
                    }

                    return ResponseEntity.ok(participantResponse);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Link
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> createLink(@PathVariable String id, @RequestBody LinkRequestPayload payload) {
        return tripService.getById(id)
                .map(trip -> {
                    LinkResponse linkResponse = linkService.register(payload, trip);
                    return ResponseEntity.ok(linkResponse);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkDataResponse>> getAllLinks(@PathVariable String id) {
        List<LinkDataResponse> links = linkService.getAllLinksFromTrip(id);

        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(links);
    }

}