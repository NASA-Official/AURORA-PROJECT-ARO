package com.nassafy.api.controller;

import com.nassafy.api.dto.jwt.TokenDto;
import com.nassafy.api.dto.req.MemberLoginReqDto;
import com.nassafy.api.dto.res.MemberLoginResDto;
import com.nassafy.api.service.JwtService;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginReqDto memberLoginRequestDto) {
        logger.debug("\t Start login");
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        TokenDto tokenDto = jwtService.login(email, password);

        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/memberInfo")
    public ResponseEntity<?> memberInfo(@RequestBody MemberLoginReqDto memberLoginRequestDto) {
        logger.debug("\t Start login");
        String email = memberLoginRequestDto.getEmail();
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            return ResponseEntity.badRequest().body("Error: Member is not exist!!");
        }

        MemberLoginResDto memberLoginResDto = MemberLoginResDto.builder()
                .email(member.get().getEmail())
                .nickname(member.get().getNickname())
                .build();

        return ResponseEntity.ok(memberLoginResDto);
    }
}
