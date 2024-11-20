package com.rocketseat.planner.service;

import com.rocketseat.planner.exception.InvalidTripDateException;
import com.rocketseat.planner.model.Trip;
import com.rocketseat.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.rocketseat.planner.enums.ExceptionDetails.INVALID_TRIP_DATE_MESSAGE;

@Service
public class TripService {

    private final TripRepository repository;

    @Autowired
    public TripService(TripRepository repository){
        this.repository = repository;
    }

    public Trip register(Trip newTrip) throws InvalidTripDateException {
        LocalDateTime startsAt = newTrip.getStartsAt();
        LocalDateTime endsAt = newTrip.getEndsAt();

        if(startsAt.isAfter(endsAt) || startsAt.equals(endsAt)){
            throw new InvalidTripDateException(INVALID_TRIP_DATE_MESSAGE.getHttpStatus().toString());
        }

        return repository.save(newTrip);
    }

    public Optional<Trip> getById(String id){
        return repository.findById(id);
    }

    public Trip update(Trip rawTrip, LocalDateTime ends_at, LocalDateTime starts_at, String destination){
        rawTrip.setEndsAt(ends_at);
        rawTrip.setStartsAt(starts_at);
        rawTrip.setDestination(destination);

        return repository.save(rawTrip);
    }

    public Trip setIsConfirmedTrue(Trip rawTrip){
        rawTrip.setIsConfirmed(true);
        return repository.save(rawTrip);
    }

}
