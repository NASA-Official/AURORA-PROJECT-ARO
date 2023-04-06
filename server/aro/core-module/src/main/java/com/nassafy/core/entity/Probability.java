package com.nassafy.core.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Probability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "probability_id")
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    private Integer prob;

    public Probability() {
    }

    public Probability(LocalDateTime dateTime, Attraction attraction,Integer prob) {
        this.dateTime = dateTime;
        this.attraction = attraction;
        this.prob = prob;
    }
}

