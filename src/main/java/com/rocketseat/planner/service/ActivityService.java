package com.rocketseat.planner.service;

import com.rocketseat.planner.model.ActivityEntity;
import com.rocketseat.planner.repository.ActivityRepository;
import com.rocketseat.planner.request.ActivityRequestPayload;
import com.rocketseat.planner.response.ActivityDataResponse;
import com.rocketseat.planner.response.ActivityResponse;
import com.rocketseat.planner.model.TripEntity;
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

    public ActivityResponse register(ActivityRequestPayload payload, TripEntity tripEntity){
        ActivityEntity newActivityEntity = repository.save(new ActivityEntity(payload.title(), payload.occurs_at(), tripEntity));
        return new ActivityResponse(newActivityEntity.getId());
    }

    public List<ActivityDataResponse> getAllActivitiesFromId(String tripId) {
        return repository.findByTripId(tripId).stream().map(activityEntity -> new ActivityDataResponse(activityEntity.getId(), activityEntity.getTitle(), activityEntity.getOccursAt())).toList();
    }
}
