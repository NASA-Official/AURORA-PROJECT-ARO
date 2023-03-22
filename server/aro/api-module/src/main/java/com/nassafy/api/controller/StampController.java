package com.nassafy.api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.res.StampDiaryResDTO;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.StampService;
import com.nassafy.core.DTO.MapStampDTO;
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
@RequestMapping("/api/stamps/")
@Slf4j
public class StampController {
    @Autowired
    private StampService stampService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("{memberId}/{nation}")
    public ResponseEntity<List<MapStampDTO>> getStampMemberAndCountry(@PathVariable Long memberId, @PathVariable String nation){
        List<MapStampDTO> mapStamps = stampService.findStampsByUserAndCountry(memberId, nation);
        return ResponseEntity.ok(mapStamps);
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
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editStampDiary (
            @RequestPart("newImageList") List<MultipartFile> newImageList,
            @RequestPart("data") StampDiaryReqDTO stampDiaryReqDTO,
            @PathVariable Long attractionId) {
        log.info("start edit diary");

        String email = jwtService.getUserEmailFromJwt();

        try {
            stampService.editStampDiary(email, attractionId, newImageList, stampDiaryReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "diary/{attractionId}")
    public ResponseEntity<?> getStampDiary (
            @PathVariable Long attractionId) {

        String email = jwtService.getUserEmailFromJwt();

        try {
            StampDiaryResDTO result = stampService.getStampDiary(attractionId, email);
            return new ResponseEntity<>(result,HttpStatus.OK);
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
}
