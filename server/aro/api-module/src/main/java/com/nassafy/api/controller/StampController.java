package com.nassafy.api.controller;


import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.req.StampDiaryResDTO;
import com.nassafy.api.service.StampService;
import com.nassafy.core.DTO.MapStampDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class StampController {
    @Autowired
    private StampService stampService;

    @GetMapping("stamps/{memberId}/{nation}")
    public ResponseEntity<List<MapStampDTO>> getStampMemberAndCountry(@PathVariable Long memberId, @PathVariable String nation){
        List<MapStampDTO> mapStamps = stampService.findStampsByUserAndCountry(memberId, nation);
        return ResponseEntity.ok(mapStamps);
    }

    @PostMapping(value = "stamps/{attractionId}/diary/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StampDiaryResDTO> createStampDiary(
            @RequestPart StampDiaryReqDTO stampDiaryReqDTO,
            @PathVariable Long attractionId,
            @PathVariable Long memberId ) throws IOException, IllegalAccessException {
        StampDiaryResDTO result = stampService.createStampDiary(attractionId, memberId, stampDiaryReqDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
