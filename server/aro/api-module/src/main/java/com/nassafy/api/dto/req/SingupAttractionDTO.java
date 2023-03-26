package com.nassafy.api.dto.req;

import lombok.Getter;

@Getter
public class SingupAttractionDTO {
    // 명소id, 명소 명, 스탬프 이미지, 명소 설명
    private Long attractionId;
    private String attractionName;
    private String stamp;
    private String description;

    public SingupAttractionDTO(Long attractionId, String attractionName, String stamp, String description) {
        this.attractionId = attractionId;
        this.attractionName = attractionName;
        this.stamp = stamp;
        this.description = description;
    }
}
