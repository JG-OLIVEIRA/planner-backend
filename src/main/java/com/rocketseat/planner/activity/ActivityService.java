package com.rocketseat.planner.activity;

import com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository repository;

    @Autowired
    public ActivityService(ActivityRepository repository){
        this.repository = repository;
    }

    public ActivityResponse register(ActivityRequestPayload payload, Trip trip){
        Activity newActivity = repository.save(new Activity(payload.title(), payload.occurs_at(), trip));
        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesFromId(UUID tripId) {
        return repository.findByTripId(tripId).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
