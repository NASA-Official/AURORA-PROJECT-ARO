package com.nassafy.api.controller;

import com.nassafy.api.dto.jwt.TokenDto;
import com.nassafy.api.dto.req.MemberLoginReqDto;
import com.nassafy.api.jwt.JwtAuthenticationFilter;
import com.nassafy.api.service.MemberService;
import com.nassafy.core.entity.RefreshToken;
import com.nassafy.core.respository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;

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
    public TokenDto login(@RequestBody MemberLoginReqDto memberLoginRequestDto) {
        logger.debug("\t Start login");
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenDto tokenDto = memberService.login(memberId, password);



        return tokenDto;
    }
}
