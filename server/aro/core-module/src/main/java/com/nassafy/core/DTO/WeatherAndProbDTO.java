package com.nassafy.core.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WeatherAndProbDTO {

    private String attractionName;

    private String weather;

    private int prob;

    @Builder
    public WeatherAndProbDTO(String attractionName, String weather, int prob) {
        this.attractionName = attractionName;
        this.weather = weather;
        this.prob = prob;
    }
}
