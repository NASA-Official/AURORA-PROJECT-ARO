package com.nassafy.api.controller;


import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.req.StampDiaryResDTO;
import com.nassafy.api.service.StampService;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.DTO.RegisterStampDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@Slf4j
public class StampController {
    @Autowired
    private StampService stampService;

    @GetMapping("stamps/{memberId}/{nation}")
    public ResponseEntity<List<MapStampDTO>> getStampMemberAndCountry(@PathVariable Long memberId, @PathVariable String nation){
        List<MapStampDTO> mapStamps = stampService.findStampsByUserAndCountry(memberId, nation);
        return ResponseEntity.ok(mapStamps);
    }

    @PostMapping(value = "/stamps/{attractionId}/diary/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StampDiaryResDTO> createStampDiary(
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("memo") String memo,
            @PathVariable Long attractionId,
            @PathVariable Long memberId ) throws IOException, IllegalAccessException {

        StampDiaryResDTO result = stampService.createStampDiary(attractionId, memberId,
                StampDiaryReqDTO.builder().memo(memo).files(files).build());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 36번 api, 명소로 위치 옮겨야 함.
     */
    @GetMapping("attractions/stamp/{nations}")
    public ResponseEntity<List<SingupAttractionDTO>> getStampCountry(@PathVariable String nations) {
        List<SingupAttractionDTO> singupAttractionDTOS = stampService.findStampsCountry(nations);
        return ResponseEntity.ok(singupAttractionDTOS);
    }
}
