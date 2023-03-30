package com.nassafy.api.controller;

import com.nassafy.api.dto.jwt.TokenDto;
import com.nassafy.api.dto.req.*;
import com.nassafy.api.dto.res.MemberLoginResDto;
import com.nassafy.api.dto.res.MemberResDto;
import com.nassafy.api.dto.res.SnsLoginResDto;
import com.nassafy.api.jwt.JwtAuthenticationFilter;
import com.nassafy.api.service.*;
import com.nassafy.core.DTO.ProviderType;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final MemberService memberService;
    private final EmailService emailService;
    private final StampService stampService;
    private final InterestService interestService;
    private final String mailCode = "123456";

    private Map<String, String> emailCode = new HashMap<>();

    /***
     * API 1
     * @param memberLoginRequestDto
     * @return tokenDto
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginReqDto memberLoginRequestDto) {
        logger.debug("\t Start login");
        String email = memberLoginRequestDto.getEmail();
        ProviderType providerType = memberLoginRequestDto.getProviderType();

        TokenDto tokenDto = jwtService.login(email, providerType);

        return ResponseEntity.ok(tokenDto);
    }

    /***
     * API 2
     * @param
     * @return memberLoginResDto
     */
    @PostMapping("/memberInfo")
    public ResponseEntity<?> memberInfo() {
        logger.debug("\t Start memberInfo");

        String email = jwtService.getUserEmailFromJwt();
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
     * API 3
     * @param signupReqDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupReqDto signupReqDto) {
        logger.debug("\t Start singup");

        memberService.create(signupReqDto);
        stampService.makeStamp(signupReqDto.getEmail());

        Long memberId = memberRepository.findByEmail(signupReqDto.getEmail()).get().getId();
        logger.debug("\t attractionIds " + signupReqDto.getAuroraPlaces());
        interestService.registerInterest(memberId, signupReqDto.getAuroraPlaces());

        TokenDto tokenDto = jwtService.login(signupReqDto.getEmail(), signupReqDto.getProviderType());
        return ResponseEntity.ok(tokenDto);
    }

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

    /***
     * API 8
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenReqDto tokenReqDto) {
        logger.debug("\t Start logout ");

        String accessToken = jwtService.logout(tokenReqDto);
        return ResponseEntity.ok(accessToken);
    }


    /***
     * API 10
     * @return
     */
    @PostMapping("/snslogin")
    public ResponseEntity<?> snslogin(@RequestBody AccessTokenDto accessTokenDto) throws JSONException, ParseException {
        logger.debug("\t Start naverlogin : " + accessTokenDto.getAccessToken() + ", type : " + accessTokenDto.getProviderType());

        ProviderType providerType = accessTokenDto.getProviderType();

        if(providerType.equals("NAVER")) {
            String url = "https://openapi.naver.com/v1/nid/me";
            String accessToken = accessTokenDto.getAccessToken();

            RestTemplate restTemplate = new RestTemplate();

            // Header set
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            String result = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class).getBody();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            String response = jsonObject.get("response").toString();

            jsonObject = (JSONObject) jsonParser.parse(response);
            String email = (String) jsonObject.get("email");

            boolean isSignup = false;
            if (memberRepository.findByEmail(email).isPresent()) {
                isSignup = true;
            }

            SnsLoginResDto snsLoginResDto = new SnsLoginResDto();
            snsLoginResDto.setProviderType(ProviderType.NAVER);
            snsLoginResDto.setEmail(email);
            snsLoginResDto.setSignup(isSignup);

            return ResponseEntity.ok(snsLoginResDto);
        }else if(providerType.equals("GITHUB")) {

            return ResponseEntity.ok("GitHub Login Success");
        }


        return ResponseEntity.ok("Not SNS Login");
    }

    @GetMapping("/naverlogin")
    public ResponseEntity<?> getNaverCode(ServletRequest request) {
        logger.debug("\t Start getNaverCode ");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        String code = request.getParameter("code");

        return ResponseEntity.ok(code);
    }


    @GetMapping("/blacklist")
    public ResponseEntity<?> getBlacklist(){
        logger.debug("\t Start getBlacklist ");
        Set<String> blacklist = JwtAuthenticationFilter.getBlacklist();
        return ResponseEntity.ok(new ArrayList<String>(blacklist));
    }

    @PostMapping("/parseInfo")
    public ResponseEntity<?> parseInfo() {
        logger.debug("\t Start parseInfo");

        String email = jwtService.getUserEmailFromJwt();
        logger.debug("\t " + email);

        MemberResDto memberResDto = new MemberResDto();
        memberResDto.setMemberResDto(memberRepository.findByEmail(email).get());

        return ResponseEntity.ok(memberResDto);
    }
}
