package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ForecastResDTO {

    private Float kp;

    private String dateTime;

    @Builder
    public ForecastResDTO(Float kp, String dateTime) {
        this.kp = kp;
        this.dateTime = dateTime;
    }
}
