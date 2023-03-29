package com.nassafy.batch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassafy.batch.config.WeatherSchedulerConfig;
import com.nassafy.batch.dto.response.WeatherResponse;
import com.nassafy.core.entity.Weather;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class OpenWeatherMapService {
    private static final String API_BASE_URL = "https://api.openweathermap.org/data/2.5/forecast";
    private final RestTemplate restTemplate;

    public OpenWeatherMapService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Weather> fetchWeatherData(float lat, float lon) {
        String apiKey = "bde174932a0010cfd281d38a28820cb2";
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", API_BASE_URL, lat, lon, apiKey);
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        return Optional.ofNullable(response)
                .map(WeatherResponse::getList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(weatherData -> {
                    Weather weather = new Weather();
                    weather.setDateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(weatherData.getDt()), ZoneOffset.UTC));
                    weather.setLatitude(lat);
                    weather.setLongitude(lon);
                    weather.setMain(weatherData.getWeather().get(0).getMain());
                    weather.setClouds(weatherData.getClouds().getAll());
                    return weather;
                }).collect(Collectors.toList());
    }
}
