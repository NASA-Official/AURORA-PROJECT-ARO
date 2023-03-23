package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StampDiaryReqDTO {
    List<String> deleteImageList;
    String memo;

    List<MultipartFile> newImageList = new ArrayList<>();

    @Builder
    public StampDiaryReqDTO(List<String> deleteImageList, String memo, List<MultipartFile> newImageList) {
        this.deleteImageList = deleteImageList;
        this.memo = memo;
        this.newImageList = newImageList;
    }
}
