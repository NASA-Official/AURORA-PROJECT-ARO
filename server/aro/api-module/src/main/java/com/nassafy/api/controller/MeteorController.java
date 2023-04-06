package com.nassafy.api.controller;

import com.nassafy.api.dto.req.CountryIdRequest;
import com.nassafy.api.dto.res.MeteorDTO;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.MeteorService;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<MeteorDTO> getInterestMeteor() {
        log.debug("getInterestMeteor");
        Long memberId = jwtService.getUserIdFromJWT();
        log.debug("******************************" + memberId);

        MeteorDTO meteorDTO = meteorService.getInterestMeteor(memberId);
        if (meteorDTO == null) {
            return new ResponseEntity<>(new MeteorDTO(), HttpStatus.OK);
        }
        return ResponseEntity.ok(meteorDTO);
    }


    // 83번 Api22
    @PostMapping("")
    public ResponseEntity<String> postInterestMeteor(@RequestBody CountryIdRequest countryIdRequest) {
        Long memberId = jwtService.getUserIdFromJWT();
        log.debug("******************************" + memberId);
        meteorService.postInterestMeteor(memberId, countryIdRequest.getCountryId());
        return ResponseEntity.ok("ok");
    }

}
