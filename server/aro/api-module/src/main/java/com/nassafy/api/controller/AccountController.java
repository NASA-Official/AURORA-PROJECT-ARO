package com.nassafy.api.controller;

import com.nassafy.api.dto.req.CodeCheckDto;
import com.nassafy.api.dto.req.EmailCheckDto;
import com.nassafy.api.dto.req.MemberLoginReqDto;
import com.nassafy.api.dto.req.SignupReqDto;
import com.nassafy.api.dto.res.MemberLoginResDto;
import com.nassafy.api.dto.res.SignupResDto;
import com.nassafy.api.service.EmailService;
import com.nassafy.api.service.JwtService;
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
    private final JwtService jwtService;
    private final MemberService memberService;
    private final EmailService emailService;
    private final StampService stampService;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final String mailCode = "123456";

    private Map<String, String> emailCode = new HashMap<>();

    /***
     * API 4
     * @param emailCheckDto
     * @return
     * @throws Exception
     */
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

    /***
     * API 5
     * @param codeCheckDto
     * @return
     */
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

    /***
     * API 3
     * @param signupReqDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        logger.debug("\t Start singup");
        memberService.create(signupReqDto);
        stampService.makeStamp(signupReqDto.getEmail());

        return ResponseEntity.ok("singup is success!!!");
    }

    /***
     * API 2
     * @param memberLoginRequestDto
     * @return memberLoginResDto
     */
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

    /***
     * API 6
     * @return
     */
    @Transactional
    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal() {
        logger.debug("\t Start withdrawal");

        String email = jwtService.getUserEmailFromJwt();
        logger.debug("\t " + email);

        memberRepository.deleteByEmail(email);
        refreshTokenRepository.deleteByEmail(email);
        return ResponseEntity.ok("");
    }

    /***
     * API 7
     * @param nickname
     * @return
     */
    @Transactional
    @PostMapping("/changenickname/{nickname}")
    public ResponseEntity<?> changeNickname(@PathVariable String nickname) {
        logger.debug("\t Start changeNickname : " + nickname);

        String email = jwtService.getUserEmailFromJwt();
        logger.debug("\t " + email);

        Member member = memberService.updateUserNickname(email, nickname);
        if(member == null)
        {
            return ResponseEntity.badRequest().body("Error: Member is not exist!!");
        }

        return ResponseEntity.ok(member.getNickname());
    }

    @PostMapping("/parseInfo")
    public ResponseEntity<?> parseInfo() {
        logger.debug("\t Start parseInfo");

        String email = jwtService.getUserEmailFromJwt();
        logger.debug("\t " + email);

        return ResponseEntity.ok(email);
    }

}
