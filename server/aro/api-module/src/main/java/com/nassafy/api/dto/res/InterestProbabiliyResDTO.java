package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InterestProbabiliyResDTO {

    private String image;

    private int prob;

    @Builder
    public InterestProbabiliyResDTO(String image, int prob) {
        this.image = image;
        this.prob = prob;
    }
}
