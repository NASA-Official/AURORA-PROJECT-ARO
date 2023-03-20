package com.nassafy.api.service;

import com.nassafy.api.dto.req.SignupReqDto;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(SignupReqDto signupReqDto){
        logger.debug("\t create");
        Member member = new Member();

        member.setEmail(signupReqDto.getEmail());
        member.setPassword(passwordEncoder.encode(signupReqDto.getPassword()));
        member.setNickname(signupReqDto.getNickname());
        member.setAuroraService(signupReqDto.isAuroraService());
        member.setMeteorService(signupReqDto.isMeteorService());

//        member.builder()
//                .email(signupReqDto.getEmail())
//                .password(passwordEncoder.encode(signupReqDto.getPassword()))
//                .nickname(signupReqDto.getNickname())
//                .auroraService(signupReqDto.isAuroraService())
//                .meteorService(signupReqDto.isMeteorService())
//                .build();

        logger.debug("\t member " + member);
        memberRepository.save(member);

        return member;
    }
}
