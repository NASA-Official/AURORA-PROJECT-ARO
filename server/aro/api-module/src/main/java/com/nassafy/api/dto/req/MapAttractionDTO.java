package com.nassafy.api.dto.req;

import lombok.Getter;

@Getter
public class MapAttractionDTO {

    private Long attraction_id;
    private String attractionName;
    private String mapImage;
    private Float latitude;
    private Float longitude;

    public MapAttractionDTO(Long attraction_id, String attractionName, String mapImage, Float latitude, Float longitude) {
        this.attraction_id = attraction_id;
        this.attractionName = attractionName;
        this.mapImage = mapImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
