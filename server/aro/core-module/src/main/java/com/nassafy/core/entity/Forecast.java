package com.nassafy.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Forecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    private Float kp;

    @Builder
    public Forecast(LocalDateTime dateTime, Float kp) {
        this.dateTime = dateTime;
        this.kp = kp;
    }

    public void updateKp(Float kp) {
        this.kp = kp;
    }
}
