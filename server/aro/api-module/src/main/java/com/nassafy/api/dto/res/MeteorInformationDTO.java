package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeteorInformationDTO {
    private String meteorName;
    private String meteorOriginalName;
    private String predictDate;

    private String constellationImage;

    private String detailImage;


    public MeteorInformationDTO(String meteorName, String meteorOriginalName, String predictDate, String constellationImage, String detailImage) {
        this.meteorName = meteorName;
        this.meteorOriginalName = meteorOriginalName;
        this.predictDate = predictDate;
        this.constellationImage = constellationImage;
        this.detailImage = detailImage;
    }
}
