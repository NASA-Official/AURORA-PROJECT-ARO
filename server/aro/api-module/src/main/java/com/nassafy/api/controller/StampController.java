package com.nassafy.api.controller;


import com.nassafy.api.service.StampService;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.DTO.RegisterStampDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("accounts/{nations}/attrations")
    public ResponseEntity<List<RegisterStampDTO>> getStampCountry(@PathVariable String nations) {
        List<RegisterStampDTO> registerStampDTOS = stampService.findStampsCountry(nations);
        return ResponseEntity.ok(registerStampDTOS);
    }
}
