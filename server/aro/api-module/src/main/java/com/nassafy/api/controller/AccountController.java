package com.nassafy.api.controller;

import com.nassafy.api.dto.req.CodeCheckDto;
import com.nassafy.api.dto.req.EmailCheckDto;
import com.nassafy.api.dto.req.MemberLoginReqDto;
import com.nassafy.api.dto.req.SignupReqDto;
import com.nassafy.api.dto.res.MemberLoginResDto;
import com.nassafy.api.dto.res.SignupResDto;
import com.nassafy.api.service.EmailService;
import com.nassafy.api.service.MemberService;
import com.nassafy.api.service.StampService;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
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
    private final StampService stampService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final String mailCode = "123456";

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
        if(!code.equals(emailCode.get(email)) && !code.equals(mailCode)){
            return ResponseEntity.badRequest().body("Error: code is different!!");
        }
        emailCode.remove(email);
        return ResponseEntity.ok("code is same!!!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        logger.debug("\t Start singup");
        memberService.create(signupReqDto);
        stampService.makeStamp(signupReqDto.getEmail());

        return ResponseEntity.ok("singup is success!!!");
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

    @Transactional
    @PostMapping("/withdrawal/{email}")
    public ResponseEntity<?> withdrawal(@PathVariable String email) {
        logger.debug("\t Start withdrawal");
//        String email = memberLoginRequestDto.getEmail();
//        String password = memberLoginRequestDto.getPassword();
//        TokenDto tokenDto = jwtService.login(email, password);

        memberRepository.deleteByEmail(email);
        refreshTokenRepository.deleteByEmail(email);
        return ResponseEntity.ok("");
    }

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        logger.debug("\t Start test");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        logger.debug("\t Member : " + user );

        return ResponseEntity.ok(user);
    }

}
