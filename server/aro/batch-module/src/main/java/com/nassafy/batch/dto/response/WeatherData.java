package com.nassafy.batch.dto.response;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherData {
    private Integer dt;
    @JsonProperty("weather")
    private List<WeatherInfo> weather;
    private Clouds clouds;
    private Integer visibility;

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @JsonProperty("coord")
    private Coordinates coordinates;

    // Getters and Setters
    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherInfo> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}

