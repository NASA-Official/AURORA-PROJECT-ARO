package com.nassafy.api.controller;

import com.nassafy.api.dto.req.MapAttractionDTO;
import com.nassafy.api.service.AttrationService;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.respository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AttrationController {
    @Autowired
    private AttrationService attrationService;

    private final AttractionRepository attractionRepository;

    @Autowired
    public AttrationController(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");}

    @GetMapping("/stamps/nations")
    public ResponseEntity<List<String>> getAllNation(){
        List<String> nations = attrationService.getAllNation();
        return ResponseEntity.ok(nations);
    }

//    @PostMapping("/stamps/attrations/{nation}")
//    public ResponseEntity<>


    @GetMapping("/stamps/attrations/{nation}")
    public ResponseEntity<List<Attraction>> getAttrationsByNation(@PathVariable String nation){
        List<Attraction> attractionList = attrationService.getAttractionByNation(nation);
        return ResponseEntity.ok(attractionList);
    }

    // 21번 api
    @GetMapping("/attraction/all")
    public ResponseEntity<List<MapAttractionDTO>> getAttrationForMap(){
        List<MapAttractionDTO> attractionDTOS = attrationService.getAttrationForMap();
        return ResponseEntity.ok(attractionDTOS);
    }

    // api test용 코드입니다. 클라용
    @GetMapping("/test/{nation}")
    public List<MapStampDTO> testCode(@PathVariable String nation){
        List<Attraction> attractions = attractionRepository.findByNation(nation);
        List<MapStampDTO> mapStamps = new ArrayList<>();
            for (Attraction attraction : attractions) {
            MapStampDTO mapStampDTO = new MapStampDTO(attraction.getColorStamp(), false);
            mapStamps.add(mapStampDTO);
        }
            return mapStamps;
    }
}
