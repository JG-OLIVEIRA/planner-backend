package com.rocketseat.planner.trip;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip = tripService.register(new Trip(payload));

        participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable String id){
        return tripService.getById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable String id, @RequestBody TripRequestPayload payload){
        return tripService.getById(id)
                .map(trip -> {
                    LocalDateTime ends_at = LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);
                    LocalDateTime starts_at = LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
                    String destination = payload.destination();

                    Trip updatedTrip = tripService.update(trip, ends_at, starts_at, destination);

                    return ResponseEntity.ok(updatedTrip);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable String id){
        return tripService.getById(id)
                .map(trip -> {
                    participantService.triggerConfirmationEmailToParticipants(id);
                    Trip updatedTrip = tripService.setIsConfirmedTrue(trip);
                    return ResponseEntity.ok(updatedTrip);
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
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable String id){
        List<ActivityData> activities = activityService.getAllActivitiesFromId(id);

        if(activities.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(activities);
    }

    //Participant
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable String id){
        List<ParticipantData> participants = participantService.getAllParticipantsFromEvent(id);

        if(participants.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(participants);
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable String id, @RequestBody ParticipantRequestPayload payload) {
        return tripService.getById(id)
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
    public ResponseEntity<LinkResponse> createLink(@PathVariable String id, @RequestBody LinkRequestPayload payload) {
        return tripService.getById(id)
                .map(trip -> {
                    LinkResponse linkResponse = linkService.register(payload, trip);
                    return ResponseEntity.ok(linkResponse);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable String id) {
        List<LinkData> links = linkService.getAllLinksFromTrip(id);

        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(links);
    }

}