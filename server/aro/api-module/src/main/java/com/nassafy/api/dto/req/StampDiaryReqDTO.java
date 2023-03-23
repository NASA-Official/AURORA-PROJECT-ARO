package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StampDiaryReqDTO {
    List<String> deleteImageList;
    String memo;

    @Builder
    public StampDiaryReqDTO(List<String> deleteImageList, String memo) {
        this.deleteImageList = deleteImageList;
        this.memo = memo;
    }
}
