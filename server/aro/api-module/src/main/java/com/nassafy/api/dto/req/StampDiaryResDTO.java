package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StampDiaryResDTO {
    List<String> images;

    String memo;

    @Builder
    public StampDiaryResDTO(List<String> images, String memo) {
        this.images = images;
        this.memo = memo;
    }
}
