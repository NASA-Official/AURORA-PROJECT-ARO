package com.nassafy.core.DTO;

import lombok.Getter;

@Getter
public class MapStampDTO {

    private Long attractionId;
    private String colorStamp;
    private boolean certification;



    public MapStampDTO(Long attractionId ,String colorStamp, boolean certification) {
        this.attractionId = attractionId;
        this.colorStamp = colorStamp;
        this.certification = certification;
    }

}
