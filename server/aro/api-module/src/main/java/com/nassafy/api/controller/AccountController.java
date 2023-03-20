package com.nassafy.api.controller;

import com.nassafy.api.dto.req.SignupReqDto;
import com.nassafy.api.dto.res.SignupResDto;
import com.nassafy.api.service.MemberService;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private final int mailCode = 123456;

    @PostMapping("/emailcheck/{email}")
    public ResponseEntity<?> emailCheck(@PathVariable("email") String email) {
        logger.debug("\t Start emailCheck " + email);
        if(memberRepository.findByEmail(email).isPresent()){
            return ResponseEntity.badRequest().body("Error: email is already used!!");
        }

        return ResponseEntity.ok("email is not used!");
    }

    @PostMapping("/codecheck/{code}")
    public ResponseEntity<?> codeCheck(@PathVariable("code") int code) {
        logger.debug("\t Start codeCheck " + code);
        System.out.println(code);
        if(code != mailCode){
            return ResponseEntity.badRequest().body("Error: code is different!!");
        }

        return ResponseEntity.ok("code is same!!!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        logger.debug("\t Start singup");
        logger.debug("\t SsignupReqDto " + signupReqDto);
        memberService.create(signupReqDto);

        return ResponseEntity.ok("singup is success!!!");
    }

    @PostMapping("/test1")
    public ResponseEntity<?> Test1() {
        logger.debug("\t Start Test1");

        return ResponseEntity.ok("test1 is success!!!");
    }

    @PostMapping("/test2")
    public ResponseEntity<?> Test2(@RequestBody SignupReqDto signupReqDto) {
        logger.debug("\t Start Test2");

        memberService.create(signupReqDto);

        return ResponseEntity.ok("test2 is success!!!");
    }
}
