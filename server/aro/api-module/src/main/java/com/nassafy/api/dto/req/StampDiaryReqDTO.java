package com.nassafy.api.dto.req;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class StampDiaryReqDTO {
    List<String> deleteImageList;
    String memo;

    MultipartFile newImageList;

    @Builder
    public StampDiaryReqDTO(String deleteImageList, String memo, MultipartFile newImageList) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        this.deleteImageList = objectMapper.readValue(deleteImageList, new TypeReference<List<String>>() {});
        this.memo = memo;
        this.newImageList = newImageList;
    }
}
