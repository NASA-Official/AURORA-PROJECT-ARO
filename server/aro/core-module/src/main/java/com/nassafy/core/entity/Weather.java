package com.nassafy.core.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Entity
@Getter
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;
    private Float latitude;
    private Float longitude;
    private String weather;
    private Integer clouds;

    public Weather() {
    }

    @JsonProperty("dt")
    public void setDateTime(Long timestamp) {
        this.dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }

    @JsonProperty("coord")
    public void setCoordinates(Coordinates coordinates) {
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
    }

    @JsonProperty("weather")
    public void setWeatherCondition(WeatherCondition[] weatherCondition) {
        this.weather = weatherCondition[0].getMain();
    }

    @JsonProperty("clouds")
    public void setClouds(Clouds clouds) {
        this.clouds = clouds.getAll();
    }
}

