package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class StampDiaryReqDTO {
    List<MultipartFile> files;
    String memo;

    @Builder
    public StampDiaryReqDTO(List<MultipartFile> files, String memo) {
        this.files = files;
        this.memo = memo;
    }
}
