package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StampDiaryResDTO {
    List<String> images;

    String memo;

    String nation;

    String attractionName;

    String description;

    @Builder
    public StampDiaryResDTO(List<String> images, String memo, String nation, String attractionName, String description) {
        this.images = images;
        this.memo = memo;
        this.nation = nation;
        this.attractionName = attractionName;
        this.description = description;
    }
}
