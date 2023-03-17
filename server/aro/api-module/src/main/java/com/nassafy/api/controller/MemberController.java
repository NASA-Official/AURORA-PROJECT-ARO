package com.nassafy.api.controller;

import com.nassafy.api.dto.jwt.TokenDto;
import com.nassafy.api.dto.req.MemberLoginReqDto;
import com.nassafy.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
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
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenDto tokenDto = memberService.login(memberId, password);
        return tokenDto;
    }
}
