package com.nassafy.api.service;

import com.nassafy.api.dto.req.SignupReqDto;
import com.nassafy.api.exception.DuplicateMemberException;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(SignupReqDto signupReqDto){
        logger.debug("\t create");

        if (memberRepository.findByEmail(signupReqDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Member member = Member.builder()
                .email(signupReqDto.getEmail())
                .password(passwordEncoder.encode(signupReqDto.getPassword()))
                .nickname(signupReqDto.getNickname())
                .auroraService(signupReqDto.isAuroraService())
                .meteorService(signupReqDto.isMeteorService())
                .alarm(true)
                .auroraDisplay(true)
                .build();
        member.getRoles().add("USER");
        logger.debug("\t member " + member);
        memberRepository.save(member);

        return member;
    }

    public Member updateUserNickname(String email, String nickname){
        logger.debug("\t updateUserNickname");

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        logger.debug("\t findByEmail : " + optionalMember.get());
        if(optionalMember.isEmpty()){
            return null;
        }
        Member member = optionalMember.get();
        logger.debug("\t before update member : " + member);


        member.setNickname(nickname);
        memberRepository.save(member);

        member = memberRepository.findByEmail(email).get();
        logger.debug("\t after update member : " + member);

        return member;
    }
}
