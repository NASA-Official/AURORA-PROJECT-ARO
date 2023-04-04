package com.nassafy.api.controller;

import com.nassafy.api.dto.res.MeteorDTO;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.MeteorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/meteor")
@RequiredArgsConstructor
@Slf4j
public class MeteorController {
    private final MeteorService meteorService;
    private final JwtService jwtService;
    // 82번 Api
    // 관심 국가 유성우 리스트 보여주기
    @GetMapping("")
    private ResponseEntity<MeteorDTO> getInterestMeteor() {
        MeteorDTO meteorDTO = meteorService.getInterestMeteor();
        return ResponseEntity.ok(meteorDTO);
    }

    // 83번 Api
    @PostMapping("")
    private ResponseEntity<String> postInterestMeteor(@RequestBody Long countryId) {
        Long memberId = jwtService.getUserIdFromJWT();
        meteorService.postInterestMeteor(memberId, countryId);
        return ResponseEntity.ok("ok");
    }
}
