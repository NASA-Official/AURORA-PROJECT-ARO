package com.nassafy.api.controller;


import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.nassafy.api.dto.req.StampDTO;
import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.res.StampDiaryResDTO;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.StampService;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.DTO.RegisterStampDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
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


//    @PostMapping(value = "diary/{nation}/{attraction}/{memberId}",
//            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> createStampDiary (
//            @RequestPart("files") List<MultipartFile> files,
//            @RequestPart("memo") String memo,
//            @PathVariable String nation,
//            @PathVariable String attraction,
//            @PathVariable Long memberId) {
//
//        log.info("start create stamp diary");
//        log.info("memo:" + memo);
//        log.info("file size: " + files.size());
//        log.info(String.valueOf(files.getClass()));
//
//        try {
//            stampService.createStampDiary(nation, attraction, memberId,
//                    StampDiaryReqDTO.builder().memo(memo).files(files).build());
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        } catch (IllegalArgumentException | IOException e) {
//            log.debug(e.getMessage());
//            log.debug("BAD REQUEST");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

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
}
