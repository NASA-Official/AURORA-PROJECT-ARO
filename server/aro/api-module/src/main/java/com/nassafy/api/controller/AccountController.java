package com.nassafy.api.controller;

import com.nassafy.api.dto.req.CodeCheckDto;
import com.nassafy.api.dto.req.EmailCheckDto;
import com.nassafy.api.dto.req.SignupReqDto;
import com.nassafy.api.dto.res.SignupResDto;
import com.nassafy.api.service.EmailService;
import com.nassafy.api.service.MemberService;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final MemberService memberService;
    private final EmailService emailService;
    private final MemberRepository memberRepository;

    private final int mailCode = 123456;

    private Map<String, String> emailCode = new HashMap<>();

    @PostMapping("/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody EmailCheckDto emailCheckDto) throws Exception {
        String email = emailCheckDto.getEmail();
        logger.debug("\t Start emailCheck " + email);
        if(memberRepository.findByEmail(email).isPresent()){
            return ResponseEntity.badRequest().body("Error: email is already used!!");
        }

        String code = emailService.sendSimpleMessage(email);
        logger.debug("\t code " + code);
        emailCode.put(email, code);
        return ResponseEntity.ok("email is not used!");
    }

    @PostMapping("/codecheck")
    public ResponseEntity<?> codeCheck(@RequestBody CodeCheckDto codeCheckDto) {
        String email = codeCheckDto.getEmail();
        String code = codeCheckDto.getCode();
        logger.debug("\t Start codeCheck " + email + ", " + code);

        if(!emailCode.containsKey(email)){
            return ResponseEntity.badRequest().body("Error: email is not checked!!");
        }
        if(!code.equals(emailCode.get(email))){
            return ResponseEntity.badRequest().body("Error: code is different!!");
        }
        emailCode.remove(email);
        return ResponseEntity.ok("code is same!!!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        logger.debug("\t Start singup");
        memberService.create(signupReqDto);

        return ResponseEntity.ok("singup is success!!!");
    }

}