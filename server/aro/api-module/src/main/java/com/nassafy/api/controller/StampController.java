package com.nassafy.api.controller;


import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.nassafy.api.dto.req.StampDTO;
import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.res.StampDiaryResDTO;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.StampService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/stamps/")
@Slf4j
public class StampController {
    @Autowired
    private StampService stampService;

    @Autowired
    private JwtService jwtService;


    /**
     * 30번 Api
     */
    @GetMapping("collectioncount/{nationName}")
    public ResponseEntity<String> getHowManyStamps(@PathVariable String nationName) {
        String count = stampService.getHowManyStamps(nationName);
        return ResponseEntity.ok(count);
    }


    /**
     * 31번 API
     */
    @GetMapping("detail/{attractionId}")
    public ResponseEntity<StampDTO> getStampDetail(@PathVariable Long attractionId) {
        StampDTO stampDTO = stampService.getStampDetail(attractionId);
        return ResponseEntity.ok(stampDTO);
    }

    @PostMapping(value = "diary/{attractionId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editStampDiary(
            StampDiaryReqDTO stampDiaryReqDTO,
            @PathVariable Long attractionId) {

        log.info("start edit diary");
        log.info(stampDiaryReqDTO.getMemo());
        log.info(String.valueOf(stampDiaryReqDTO.getDeleteImageList().size()));
        log.info(String.valueOf(stampDiaryReqDTO.getNewImageList().getClass()));

        String email = jwtService.getUserEmailFromJwt();

        try {
            stampService.editStampDiary(email, attractionId, stampDiaryReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "diary/{attractionId}")
    public ResponseEntity<?> getStampDiary(
            @PathVariable Long attractionId) {

        String email = jwtService.getUserEmailFromJwt();

        try {
            StampDiaryResDTO result = stampService.getStampDiary(attractionId, email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.debug(e.getMessage());
            log.debug("BAD REQUEST");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * 36번 api
     */
    @GetMapping("signup/{nation}")
    public ResponseEntity<List<SingupAttractionDTO>> getStampCountry(@PathVariable String nation) {
        List<SingupAttractionDTO> singupAttractionDTOS = stampService.findStampsCountry(nation);
        return ResponseEntity.ok(singupAttractionDTOS);
    }


    // 37번 api
    @GetMapping("detail/all/{nationName}")
    public ResponseEntity<List<StampDTO>> getAllStampdetail(@PathVariable String nationName) {
        List<StampDTO> stampDTOS = stampService.findAllStampdetail(nationName);
        return ResponseEntity.ok(stampDTOS);
    }


    // 32번 api
    @PostMapping("certification/{attractionId}")
    public ResponseEntity<?> updateCertification (@PathVariable Long attractionId) {
        String email = jwtService.getUserEmailFromJwt();
        try {
            stampService.updateCertification(attractionId, email);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
