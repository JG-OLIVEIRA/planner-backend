package com.rocketseat.planner.service;

import com.rocketseat.planner.model.Activity;
import com.rocketseat.planner.repository.ActivityRepository;
import com.rocketseat.planner.request.ActivityRequestPayload;
import com.rocketseat.planner.response.ActivityDataResponse;
import com.rocketseat.planner.response.ActivityResponse;
import com.rocketseat.planner.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ActivityDataResponse> getAllActivitiesFromId(String tripId) {
        return repository.findByTripId(tripId).stream().map(activity -> new ActivityDataResponse(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
