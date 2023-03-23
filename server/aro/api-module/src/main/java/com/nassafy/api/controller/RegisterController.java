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

        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
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
        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        return ResponseEntity.ok(member.getAuroraService());
    }

    /***
     * API 13 - 유성우 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/service/meteor")
    public ResponseEntity<Boolean> getMeteorService(){
        logger.debug("\t Start getMeteorService ");
        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        return ResponseEntity.ok(member.getMeteorService());
    }

    /***
     *API 14 - 알람 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/alarm")
    public ResponseEntity<Boolean> getAlarm(){
        logger.debug("\t Start getAlarm ");

        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        return ResponseEntity.ok(member.getAlarm());
    }

    /***
     * API 15
     * @return
     */
    @PostMapping("/alarm")
    public ResponseEntity<Void> toggleAlarm() {
        logger.debug("\t Start toggleAlarm ");

        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
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
        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        return ResponseEntity.ok(member.getAuroraDisplay());
    }

    /***
     * API 17
     * @return
     */
    @PostMapping("/auroraDisplay")
    public ResponseEntity<Void> toggleAuroraDisplay(){
        logger.debug("\t Start toggleAuroraDisplay ");

        String email = jwtService.getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );

        member.toggleAuroraDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

}
