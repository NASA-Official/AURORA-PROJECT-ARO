package com.nassafy.api.service;

import com.nassafy.api.dto.res.ForecastAndInterestResDTO;
import com.nassafy.api.dto.res.ForecastResDTO;
import com.nassafy.core.DTO.WeatherAndProbDTO;
import com.nassafy.core.entity.Forecast;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.ForecastRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastService {

    private final ForecastRepository forecastRepository;

    private final AttractionRepository attractionRepository;

    public ForecastResDTO getKP(LocalDateTime dateTime) {
        try {
            Forecast forecast = forecastRepository.findByDateTime(dateTime).orElseThrow(IllegalArgumentException::new);
            return ForecastResDTO.builder().kp(forecast.getKp()).dateTime(forecast.getDateTime().toString()).build();
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info("해당 시간은 존재하지 않습니다.");
            return null;
        }
    }

    public ForecastAndInterestResDTO getKpsAndInterests(LocalDateTime dateTime, Long memberId) {
        List<Forecast> forecasts = forecastRepository.findAll();
        int start = 0;
        int end = 0;
        log.info(dateTime.toString());

        for (int i = forecasts.size() - 1; i >= 0; i--) {
            if (forecasts.get(i).getDateTime().isEqual(dateTime)) {
                if (forecasts.size() - i >= 24) {
                    start = i;
                    end = i + 24;
                }
                else {
                    start = i - (24 - (forecasts.size() - i));
                    end = forecasts.size();
                }
            }
        }

        if (start == 0 && end == 0) {
            log.info("해당 시간은 존재하지 않습니다. ");
            throw new IllegalArgumentException();
        }

        log.info("kp ok");

        List<Float> kps = new ArrayList<>();

        for (Forecast forecast: forecasts.subList(start, end)) {
            log.info(forecast.getDateTime().toString() + " : " + forecast.getKp());
            kps.add(forecast.getKp());
        }

        List<WeatherAndProbDTO> weatherAndProbList = attractionRepository.findWeatherAndProbList(dateTime, memberId);
        Collections.sort(weatherAndProbList, Collections.reverseOrder());
        return ForecastAndInterestResDTO.builder().probs(weatherAndProbList).kps(kps).build();
    }



}
