package com.rocketseat.planner.service;

import com.rocketseat.planner.model.TripEntity;
import com.rocketseat.planner.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository repository;

    @Autowired
    public TripService(TripRepository repository){
        this.repository = repository;
    }

    public TripEntity register(TripEntity newTripEntity){
        return repository.save(newTripEntity);
    }

    public Optional<TripEntity> getById(String id){
        return repository.findById(id);
    }

    public TripEntity update(TripEntity rawTripEntity, LocalDateTime ends_at, LocalDateTime starts_at, String destination){
        rawTripEntity.setEndsAt(ends_at);
        rawTripEntity.setStartsAt(starts_at);
        rawTripEntity.setDestination(destination);

        return repository.save(rawTripEntity);
    }

    public TripEntity setIsConfirmedTrue(TripEntity rawTripEntity){
        rawTripEntity.setIsConfirmed(true);
        return repository.save(rawTripEntity);
    }

}
