package com.nassafy.api.dto.req;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class StampDiaryReqDTO {
    List<String> deleteImageList;
    String memo;

    List<MultipartFile> newImageList;

    @Builder
    public StampDiaryReqDTO(String deleteImageList, String memo, List<MultipartFile> newImageList) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        this.deleteImageList = objectMapper.readValue(deleteImageList, new TypeReference<List<String>>() {});
        log.info(String.valueOf(newImageList.getClass()));
        this.memo = memo;
        this.newImageList = newImageList;
//        this.newImageList = objectMapper.readValue((JsonParser) newImageList,new TypeReference<List<MultipartFile>>() {});
    }
}
