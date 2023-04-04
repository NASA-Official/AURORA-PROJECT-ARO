package com.nassafy.api.controller;

import com.nassafy.api.dto.req.CollectionsDTO;
import com.nassafy.api.dto.req.MapAttractionDTO;
import com.nassafy.api.service.AttrationService;
import com.nassafy.core.DTO.InterestProbabilityDTO;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.respository.AttractionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/attractions")
@Slf4j
public class AttrationController {
    @Autowired
    private AttrationService attrationService;

    private final AttractionRepository attractionRepository;

    @Autowired
    public AttrationController(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    // 20번 Api
    @GetMapping("/all/nations")
    public ResponseEntity<List<String>> getAllNation(){
        List<String> nations = attrationService.getAllNation();
        return ResponseEntity.ok(nations);
    }


    // 21번 api
    @GetMapping("/all")
    public ResponseEntity<List<MapAttractionDTO>> getAttrationForMap(){
        List<MapAttractionDTO> attractionDTOS = attrationService.getAttrationForMap();
        return ResponseEntity.ok(attractionDTOS);
    }


    // 22번 api
    @GetMapping("/{nation}")
    public List<MapStampDTO> getStampsFormember(@PathVariable String nation){
        List<MapStampDTO> mapStampDTOS = attrationService.getStampsFormember(nation);
        return mapStampDTOS;
    }


    // 23번 api
    @GetMapping("/mapImage/{nationName}")
    public ResponseEntity<String> getMapImage(@PathVariable String nationName) {
        String mapImage = attrationService.getMapImage(nationName);
        return ResponseEntity.ok(mapImage);


    }

    // 24번 api
    @GetMapping("/stamps/total/{nation}")
    public ResponseEntity<Integer> getStampCountCountry(@PathVariable  String nation) {
        Integer total = attrationService.getStampCountCountry(nation);
        return ResponseEntity.ok(total);
    }



    // 25번 api
    @GetMapping("/stamps/myCount/{nation}")
    public ResponseEntity<Integer> getMyStampCountCountry(@PathVariable String nation) {
        Integer myCount = attrationService.getMyStampCountCountry(nation);
        return ResponseEntity.ok(myCount);
    }


     // 26번 api
    @GetMapping("/stamps/collections/{nation}")
    public ResponseEntity<CollectionsDTO> getCollections(@PathVariable String nation) {
        CollectionsDTO collectionsDTO = attrationService.getCollections(nation);
        return ResponseEntity.ok(collectionsDTO);
    }


    // 테스트
    @GetMapping("/stamps/attrations/{nation}")
    public ResponseEntity<List<Attraction>> getAttrationsByNation(@PathVariable String nation){
        List<Attraction> attractionList = attrationService.getAttractionByNation(nation);
        return ResponseEntity.ok(attractionList);
    }

    @GetMapping("/probability/{date}/{time}")
    public ResponseEntity<List<InterestProbabilityDTO>> getProbability(@PathVariable String date, @PathVariable int time) {
        String[] dates = date.split("-");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), time, 0);

        try {
            List<InterestProbabilityDTO> result = attrationService.getProbability(dateTime);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
