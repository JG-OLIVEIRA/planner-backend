package com.rocketseat.planner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "participants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantEntity {

    @Id
    private String id;

    @Column(name = "is_Confirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private TripEntity tripEntity;

    public ParticipantEntity(String email, TripEntity tripEntity){
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.tripEntity = tripEntity;
        this.isConfirmed = false;
        this.name = "";
    }
}
