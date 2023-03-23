package com.nassafy.api.controller;


import com.nassafy.api.service.JwtService;
import com.nassafy.core.DTO.ServiesRegisterDTO;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtService jwtService;

    /***
     * API 11
     * @param serviesRegisterDTO
     * @return
     */
    @PostMapping("/service")
    public ResponseEntity<Void> serviceRegiser(@RequestBody ServiesRegisterDTO serviesRegisterDTO) {
        logger.debug("\t Start serviceRegister ");

        Member member = jwtService.getUserFromEmail();
        if (member.getAuroraService() != serviesRegisterDTO.getAuroraService()) {
            member.toggleAuroraService();
        }
        if (member.getMeteorService() != serviesRegisterDTO.getMeteorService()) {
            member.toggleMeteorService();
        }
        memberRepository.save(member);
        return ResponseEntity.noContent().build();

    }

    /***
     * API 12 - 오로라 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/service/aurora")
    public ResponseEntity<Boolean> getAuroraService() {
        logger.debug("\t Start getAuroraService ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getAuroraService());
    }

    /***
     * API 13 - 유성우 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/service/meteor")
    public ResponseEntity<Boolean> getMeteorService(){
        logger.debug("\t Start getMeteorService ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getMeteorService());
    }

    /***
     *API 14 - 알람 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/alarm")
    public ResponseEntity<Boolean> getAlarm(){
        logger.debug("\t Start getAlarm ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getAlarm());
    }

    /***
     * API 15
     * @return
     */
    @PostMapping("/alarm")
    public ResponseEntity<Void> toggleAlarm() {
        logger.debug("\t Start toggleAlarm ");
        Member member = jwtService.getUserFromEmail();
        member.toggleAlarm();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

    /***
     * API 16 - 오로라 표시 여부 조회
     * @return
     */
    @GetMapping("/auroraDisplay")
    public ResponseEntity<Boolean> getAuroraDisplay(){
        logger.debug("\t Start getAuroraDisplay ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getAuroraDisplay());
    }

    /***
     * API 17
     * @return
     */
    @PostMapping("/auroraDisplay")
    public ResponseEntity<Void> toggleAuroraDisplay(){
        logger.debug("\t Start toggleAuroraDisplay ");
        Member member = jwtService.getUserFromEmail();
        member.toggleAuroraDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

}
