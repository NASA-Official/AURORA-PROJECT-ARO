package com.nassafy.api.controller;


import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.nassafy.api.dto.req.StampDTO;
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

    // 30번 Api
    @GetMapping("{nation}/{memberId}")
    public ResponseEntity<List<MapStampDTO>> getStampMemberAndCountry(@PathVariable Long memberId, @PathVariable String nation){
        List<MapStampDTO> mapStamps = stampService.findStampsByUserAndCountry(memberId, nation);
        return ResponseEntity.ok(mapStamps);
    }


    /**
     *  31번 API
     */
    @GetMapping("detail/{attractionId}/{memberId}")
    public ResponseEntity<StampDTO> getStampDetail(@PathVariable Long attractionId, @PathVariable Long memberId) {
        StampDTO stampDTO = stampService.getStampDetail(attractionId, memberId);
        return ResponseEntity.ok(stampDTO);
    }

    @PostMapping(value = "diary/{nation}/{attraction}/{memberId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createStampDiary (
            @RequestPart("files") List<MultipartFile> files,
            @RequestPart("memo") String memo,
            @PathVariable String nation,
            @PathVariable String attraction,
            @PathVariable Long memberId) {

        log.info("start create stamp diary");
        log.info("memo:" + memo);
        log.info("file size: " + files.size());
        log.info(String.valueOf(files.getClass()));

        try {
            stampService.createStampDiary(nation, attraction, memberId,
                    StampDiaryReqDTO.builder().memo(memo).files(files).build());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException | IOException e) {
            log.debug(e.getMessage());
            log.debug("BAD REQUEST");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "diary/{nation}/{attraction}/{memberId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editStampDiary (
            @RequestPart("newImageLists") List<MultipartFile> newImageLists,
            @RequestPart("deleteImageLists") String deleteImageLists,
            @RequestPart("memo") String memo,
            @PathVariable String nation,
            @PathVariable String attraction,
            @PathVariable Long memberId) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<String> deleteImageListsObject = mapper.readValue(deleteImageLists, new TypeReference<List<String>>() {
        });

        try {
            stampService.editStampDiary(nation, attraction, memberId, newImageLists, deleteImageListsObject, memo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "diary/{nation}/{attraction}/{memberId}")
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


    /**
     * 36번 api
     */
    @GetMapping("signup/{nation}")
    public ResponseEntity<List<SingupAttractionDTO>> getStampCountry(@PathVariable String nation) {
        List<SingupAttractionDTO> singupAttractionDTOS = stampService.findStampsCountry(nation);
        return ResponseEntity.ok(singupAttractionDTOS);
    }
}
