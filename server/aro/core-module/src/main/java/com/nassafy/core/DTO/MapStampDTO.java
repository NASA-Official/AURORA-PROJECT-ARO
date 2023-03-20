package com.nassafy.core.DTO;

import lombok.Getter;

@Getter
public class MapStampDTO {
    private String colorStamp;
    private boolean certification;

    public MapStampDTO(String colorStamp, boolean certification) {
        this.colorStamp = colorStamp;
        this.certification = certification;
    }

}
