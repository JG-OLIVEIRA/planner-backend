package com.rocketseat.planner.service;

import java.util.List;

import com.rocketseat.planner.model.LinkEntity;
import com.rocketseat.planner.repository.LinkRepository;
import com.rocketseat.planner.request.LinkRequestPayload;
import com.rocketseat.planner.response.LinkDataResponse;
import com.rocketseat.planner.response.LinkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.model.TripEntity;

@Service
public class LinkService {

    private final LinkRepository repository;

    @Autowired
    public LinkService(LinkRepository repository){
        this.repository = repository;
    }

    public LinkResponse register(LinkRequestPayload payload, TripEntity tripEntity){
        LinkEntity newLinkEntity = repository.save(new LinkEntity(payload.title(), payload.url(), tripEntity));
        return new LinkResponse(newLinkEntity.getId());
    }
    public List<LinkDataResponse> getAllLinksFromTrip(String tripId){
        return repository.findByTripId(tripId).stream().map(linkEntity -> new LinkDataResponse(linkEntity.getId(), linkEntity.getTitle(), linkEntity.getUrl())).toList();
    }

}