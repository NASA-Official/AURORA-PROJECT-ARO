package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AttractionInterestOrNotDTO {
    public Long interestId;
    public Long attractionId;
    public String stamp;
    public String attractionName;
    public String description;
    public Boolean interest = false;

    @Builder
    public AttractionInterestOrNotDTO(Long interestId, Long attractionId, String stamp, String attractionName, String description, Boolean interest) {
        this.interestId = interestId;
        this.attractionId = attractionId;
        this.stamp = stamp;
        this.attractionName = attractionName;
        this.description = description;
        this.interest = interest;
    }
}
