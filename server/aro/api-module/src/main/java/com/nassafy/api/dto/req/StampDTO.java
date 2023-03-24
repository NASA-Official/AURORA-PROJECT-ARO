package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

//31번
@Getter
public class StampDTO {
    public Long attractionId;
    public String attractionName;
    public String description;
    public Boolean certification;
    private String Auth;
    private String stamp;

    @Builder
    public StampDTO(Long attractionId, String attractionName, String description, Boolean certification, String Auth, String stamp) {
        this.attractionId = attractionId;
        this.attractionName = attractionName;
        this.description = description;
        this.certification = certification;
        this.Auth = Auth;
        this.stamp = stamp;
    }
}
