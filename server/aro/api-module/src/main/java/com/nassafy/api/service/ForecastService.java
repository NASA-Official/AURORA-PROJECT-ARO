package com.nassafy.api.service;

import com.nassafy.api.dto.res.ForecastResDTO;
import com.nassafy.core.entity.Forecast;
import com.nassafy.core.respository.ForecastRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastService {

    private final ForecastRepository forecastRepository;

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
}
