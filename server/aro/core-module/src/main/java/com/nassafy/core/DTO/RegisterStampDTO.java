package com.nassafy.core.DTO;

import lombok.Getter;

@Getter
public class RegisterStampDTO {
    private String colorStamp;

    private String attractionName;

    private String description;

    public RegisterStampDTO(String colorStamp, String attractionName, String description){
        this.colorStamp = colorStamp;
        this.attractionName = attractionName;
        this.description = description;

    }
}
