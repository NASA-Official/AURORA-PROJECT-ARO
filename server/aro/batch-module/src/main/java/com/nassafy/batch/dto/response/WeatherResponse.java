package com.nassafy.batch.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonProperty("list")
    private List<WeatherData> list;
    public List<WeatherData> getList() {
        return list;
    }
}

