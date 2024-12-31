package com.rocketseat.planner.service;

import java.util.List;

import com.rocketseat.planner.model.Link;
import com.rocketseat.planner.repository.LinkRepository;
import com.rocketseat.planner.request.LinkRequestPayload;
import com.rocketseat.planner.response.LinkDataResponse;
import com.rocketseat.planner.response.LinkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.model.Trip;

@Service
public class LinkService {

    private final LinkRepository repository;

    @Autowired
    public LinkService(LinkRepository repository){
        this.repository = repository;
    }

    public LinkResponse register(LinkRequestPayload payload, Trip trip){
        Link newLink = repository.save(new Link(payload.title(), payload.url(), trip));
        return new LinkResponse(newLink.getId());
    }
    public List<LinkDataResponse> getAllLinksFromTrip(String tripId){
        return repository.findByTripId(tripId).stream().map(link -> new LinkDataResponse(link.getId(), link.getTitle(), link.getUrl())).toList();
    }

}