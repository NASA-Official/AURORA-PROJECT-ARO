package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StampDiaryResDTO {

    Long stampId;

    List<String> images;

    String memo;

    @Builder
    public StampDiaryResDTO(Long stampId, List<String> images, String memo) {
        this.stampId = stampId;
        this.images = images;
        this.memo = memo;
    }
}
