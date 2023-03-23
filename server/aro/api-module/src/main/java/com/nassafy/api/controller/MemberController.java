package com.nassafy.api.controller;

import com.nassafy.api.dto.jwt.TokenDto;
import com.nassafy.api.dto.req.MemberLoginReqDto;
import com.nassafy.api.dto.res.MemberLoginResDto;
import com.nassafy.api.service.JwtService;
import com.nassafy.core.DTO.ServiesRegisterDTO;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/test")
    public String posttest() {
        return "post success";
    }

    @GetMapping("/test")
    public String gettest() {
        return "get success";
    }


    /***
     * API 1
     * @param memberLoginRequestDto
     * @return tokenDto
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginReqDto memberLoginRequestDto) {
        logger.debug("\t Start login");
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenDto tokenDto = jwtService.login(email, password);

        return ResponseEntity.ok(tokenDto);
    }

    // 11번 Api
    @PutMapping("/register/servies/{member_id}")
    public ResponseEntity<Void> serviesRegiser(@PathVariable Long member_id, @RequestParam ServiesRegisterDTO serviesRegisterDTO) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        if (member.getAuroraService() != serviesRegisterDTO.getAuroraServise()) {
            member.toggleAuroraService();
        }
        if (member.getMeteorService() != serviesRegisterDTO.getMeteorServise()) {
            member.toggleMeteorService();
        }
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }


    // 오로라 서비스 등록 여부 조회
    // 12번 Api
    @GetMapping("/servies/aurora/{member_id}")
    public ResponseEntity<Boolean> getAuroraService(@PathVariable Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다.")
        );
        return ResponseEntity.ok(member.getAuroraService());
    }


    // 유성우 서비스 등록 여부 조회
    // 13번 Api
    @GetMapping("/servies/meteor/{member_id}")
    public ResponseEntity<Boolean> getMeteorService(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다")
        );
        return ResponseEntity.ok(member.getMeteorService());
    }

    // 알람 서비스 등록 여부 조회
    // 14번 Api
    @GetMapping("/toggleAlarm/{member_id}")
    public ResponseEntity<Boolean> getAlarm(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다")
        );
        return ResponseEntity.ok(member.getAlarm());
    }


    // 15번 Api
    @PutMapping("/toggleAlarm/{member_id}")
    public ResponseEntity<Void> toggleAlarm(@PathVariable Long member_id) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다."));
        member.toggleAlarm();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }


    // 오로라 표시 여부 조회
    // 16번 Api
    @GetMapping("/auroraDisplay/{member_id}")
    public ResponseEntity<Boolean> getAuroraDisplay(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다")
        );
        return ResponseEntity.ok(member.getAuroraDisplay());
    }


    // 17번 Api
    @PutMapping("/auroraDisplay/{member_id}")
    public ResponseEntity<Void> toggleAuroraDisplay(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다."));
        member.toggleAuroraDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

}
