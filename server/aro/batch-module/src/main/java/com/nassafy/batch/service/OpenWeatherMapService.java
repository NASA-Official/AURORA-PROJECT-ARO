package com.nassafy.batch.service;

import com.google.api.client.util.Value;
import com.nassafy.batch.dto.response.WeatherResponse;
import com.nassafy.core.entity.Weather;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherMapService {
    private static final String API_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";

    @Value("${openweathermap.api-key}")
    private String apiKey;

    public Weather[] fetchWeatherData(float lat, float lon) {
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", API_BASE_URL, lat, lon, apiKey);
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        return response != null ? response.getWeatherArray() : new Weather[0];
    }
}
