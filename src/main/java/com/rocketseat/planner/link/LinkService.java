package com.rocketseat.planner.link;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.planner.trip.Trip;

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
    public List<LinkData> getAllLinksFromTrip(String tripId){
        return repository.findByTripId(tripId).stream().map(link -> new LinkData(link.getId(), link.getTitle(), link.getUrl())).toList();
    }

}