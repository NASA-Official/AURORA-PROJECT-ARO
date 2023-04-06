package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

//31번
@Getter
public class StampDTO {
    public Long attractionId;
    public String attractionName;

    public String attractionOriginalName;
    public String description;
    public Boolean certification;
    private String Auth;
    private String stamp;
    private String certificationDate;

    @Builder
    public StampDTO(Long attractionId, String attractionName, String attractionOriginalName, String description, Boolean certification, String Auth, String stamp, String certificationDate) {
        this.attractionId = attractionId;
        this.attractionName = attractionName;
        this.attractionOriginalName = attractionOriginalName;
        this.description = description;
        this.certification = certification;
        this.Auth = Auth;
        this.stamp = stamp;
        this.certificationDate = certificationDate;
    }
}
