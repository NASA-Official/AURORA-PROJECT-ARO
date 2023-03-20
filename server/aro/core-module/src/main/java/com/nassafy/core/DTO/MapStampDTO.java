package com.nassafy.core.DTO;

import lombok.Getter;

@Getter
public class MapStampDTO {
    private String colorStamp;
    private String mapImage;
    private boolean certification;

    public MapStampDTO(String colorStamp, String mapImage, boolean certification) {
        this.colorStamp = colorStamp;
        this.mapImage = mapImage;
        this.certification = certification;
    }

}
