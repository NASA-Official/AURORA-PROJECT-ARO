package com.nassafy.api.controller;


import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.res.StampDiaryResDTO;
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

    @PostMapping(value = "stamps/diary/{nation}/{attraction}/{memberId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createStampDiary (
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("memo") String memo,
            @PathVariable String nation,
            @PathVariable String attraction,
            @PathVariable Long memberId) {

        try {
            stampService.createStampDiary(nation, attraction, memberId,
                    StampDiaryReqDTO.builder().memo(memo).files(files).build());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException | IOException e) {
            log.debug(e.getMessage());
            log.debug("BAD REQUEST");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "stamps/diary/{nation}/{attraction}/{memberId}")
    public ResponseEntity<?> getStampDiary (
            @PathVariable String nation,
            @PathVariable String attraction,
            @PathVariable Long memberId) {

        try {
            StampDiaryResDTO result = stampService.getStampDiary(nation, attraction, memberId);
            return new ResponseEntity<>(result,HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
            log.debug("BAD REQUEST");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("accounts/{nations}/attrations")
    public ResponseEntity<List<RegisterStampDTO>> getStampCountry(@PathVariable String nations) {
        List<RegisterStampDTO> registerStampDTOS = stampService.findStampsCountry(nations);
        return ResponseEntity.ok(registerStampDTOS);
    }
}
