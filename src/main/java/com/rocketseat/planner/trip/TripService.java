package com.rocketseat.planner.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    public Trip createTrip(Trip newTrip){
        repository.save(newTrip);
        return newTrip;
    }

    public Optional<Trip> getTripById(UUID id){
        return repository.findById(id);
    }

    public Trip updateTrip(
            Trip rawTrip,
            LocalDateTime ends_at,
            LocalDateTime starts_at,
            String destination
    ){
        rawTrip.setEndsAt(ends_at);
        rawTrip.setStartsAt(starts_at);
        rawTrip.setDestination(destination);

        repository.save(rawTrip);

        return rawTrip;
    }

    public Trip updateTripStatus(Trip rawTrip){
        rawTrip.setIsConfirmed(true);

        repository.save(rawTrip);

        return rawTrip;
    }


}
