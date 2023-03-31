package com.nassafy.api.controller;

import com.nassafy.api.dto.res.ForecastResDTO;
import com.nassafy.api.service.ForecastService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/forecast")
@RequiredArgsConstructor
@Slf4j
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping("/{date}/{time}")
    public ResponseEntity<ForecastResDTO> getKP(@PathVariable String date, @PathVariable int time) {
        String[] dates = date.split("-");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), time, 0);

        log.info(dateTime.toString());

        ForecastResDTO forecastResDTO = forecastService.getKP(dateTime);

        if (forecastResDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(forecastResDTO, HttpStatus.OK);
        }
    }
}
