package com.nassafy.batch.dto.response;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherData {
    private Integer dt;
    @JsonProperty("weather")
    private List<WeatherInfo> weather;
    private Clouds clouds;
    @JsonProperty("coord")
    private Coordinates coordinates;

    public Integer getDt() {
        return dt;
    }
    public List<WeatherInfo> getWeather() {
        return weather;
    }
    public Clouds getClouds() {
        return clouds;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

