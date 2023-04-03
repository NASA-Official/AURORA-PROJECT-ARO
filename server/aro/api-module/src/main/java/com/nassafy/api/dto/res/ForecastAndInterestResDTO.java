package com.nassafy.api.dto.res;

import com.nassafy.core.DTO.WeatherAndProbDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ForecastAndInterestResDTO {

    List<Float> kps;

    List<WeatherAndProbDTO> probs;

    @Builder
    public ForecastAndInterestResDTO(List<Float> kps, List<WeatherAndProbDTO> probs) {
        this.kps = kps;
        this.probs = probs;
    }
}
