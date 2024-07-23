package com.rocketseat.planner.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.planner.activity.ActivityData;
import com.rocketseat.planner.activity.ActivityRequestPayload;
import com.rocketseat.planner.activity.ActivityResponse;
import com.rocketseat.planner.activity.ActivityService;
import com.rocketseat.planner.link.LinkData;
import com.rocketseat.planner.link.LinkRequestPayload;
import com.rocketseat.planner.link.LinkResponse;
import com.rocketseat.planner.link.LinkService;
import com.rocketseat.planner.participant.ParticipantCreateResponse;
import com.rocketseat.planner.participant.ParticipantData;
import com.rocketseat.planner.participant.ParticipantRequestPayload;
import com.rocketseat.planner.participant.ParticipantService;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private ParticipantService participantService;

    //Trip
    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = tripService.createTrip(new Trip(payload));

        participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
        return tripService.getTripById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload){
        return tripService.getTripById(id)
                .map(trip -> {
                    LocalDateTime ends_at = LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDateTime starts_at = LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
                    String destination = payload.destination();

                    Trip updatedTrip = tripService.updateTrip(trip, ends_at, starts_at, destination);

                    return ResponseEntity.ok(updatedTrip);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id){
        return tripService.getTripById(id)
                .map(trip -> {
                    participantService.triggerConfirmationEmailToParticipants(id);
                    Trip updatedTrip = tripService.updateTripStatus(trip);
                    return ResponseEntity.ok(updatedTrip);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Activity
    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        return tripService.getTripById(id)
                .map(trip -> {
                    ActivityResponse activityResponse = activityService.registerActivity(payload, trip);
                    return ResponseEntity.ok(activityResponse);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id){
        List<ActivityData> activities = activityService.getAllActivitiesFromId(id);

        if(activities.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(activities);
    }

    //Participant
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id){
        List<ParticipantData> participants = participantService.getAllParticipantsFromEvent(id);

        if(participants.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        return tripService.getTripById(id)
                .map(trip -> {
                    ParticipantCreateResponse participantResponse = participantService.registerParticipantToEvent(payload.email(), trip);

                    if(trip.getIsConfirmed()) {
                        participantService.triggerConfirmationEmailToParticipant(payload.email());
                    }

                    return ResponseEntity.ok(participantResponse);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Link
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        return tripService.getTripById(id)
                .map(trip -> {
                    LinkResponse linkResponse = linkService.registerLink(payload, trip);
                    return ResponseEntity.ok(linkResponse);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        List<LinkData> links = linkService.getAllLinksFromTrip(id);

        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(links);
    }

}