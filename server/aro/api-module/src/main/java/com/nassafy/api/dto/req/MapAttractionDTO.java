package com.nassafy.api.dto.req;

import lombok.Getter;

@Getter
public class MapAttractionDTO {

    private Long attractionId;
    private String attractionName;
    private String mapImage;
    private Float latitude;
    private Float longitude;

    public MapAttractionDTO(Long attractionId, String attractionName, String mapImage, Float latitude, Float longitude) {
        this.attractionId = attractionId;
        this.attractionName = attractionName;
        this.mapImage = mapImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
