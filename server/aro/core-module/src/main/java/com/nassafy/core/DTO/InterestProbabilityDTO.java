package com.nassafy.core.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestProbabilityDTO {

    private String attractionName;

    private Float latitude;

    private Float longitude;

    private String image;

    private Integer prob;

    @Builder
    public InterestProbabilityDTO(String attractionName, Integer prob, Float latitude, Float longitude, String image) {
        this.attractionName = attractionName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.prob = prob;
    }
}
