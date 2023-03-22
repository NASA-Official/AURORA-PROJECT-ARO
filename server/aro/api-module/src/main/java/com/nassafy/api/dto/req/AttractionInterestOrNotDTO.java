package com.nassafy.api.dto.req;

import lombok.Getter;

@Getter
public class AttractionInterestOrNotDTO {
    public Long attractionId;
    public String stamp;
    public String attractionName;
    public String desciption;
    public Boolean interest = false;

    public AttractionInterestOrNotDTO(Long attractionId, String stamp, String attractionName, String desciption, Boolean interest) {
        this.attractionId = attractionId;
        this.stamp = stamp;
        this.attractionName = attractionName;
        this.desciption = desciption;
        this.interest = interest;
    }
}
