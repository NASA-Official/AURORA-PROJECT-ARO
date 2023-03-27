package com.nassafy.batch.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nassafy.core.entity.Weather;

public class WeatherResponse {
    @JsonProperty("list")
    private Weather[] weatherArray;

    public Weather[] getWeatherArray() {
        return weatherArray;
    }

    public void setWeatherArray(Weather[] weatherArray) {
        this.weatherArray = weatherArray;
    }
}
