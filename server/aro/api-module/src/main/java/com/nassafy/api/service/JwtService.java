package com.nassafy.api.service;

import com.nassafy.api.dto.req.TokenReqDto;
import com.nassafy.api.jwt.JwtAuthenticationFilter;
import com.nassafy.api.jwt.JwtTokenProvider;
import com.nassafy.api.dto.jwt.TokenDto;
import com.nassafy.core.DTO.ProviderType;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private Set<String> blacklist = new HashSet<>();

    @Transactional
    public TokenDto login(String email, String password, ProviderType providerType) {
        logger.debug("\t Start login");

        Member member = memberRepository.findByEmailAndAndProviderType(email, providerType).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다.")
        );

        logger.debug("\t email, password : " + email + ", " + password);

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);

        String refreshToken = tokenDto.getRefreshToken();
        member.setRefreshToken(refreshToken);

        memberRepository.save(member);

        return tokenDto;
    }

    public String logout(TokenReqDto tokenReqDto) {
        logger.debug("\t Start logout");

        if(!jwtTokenProvider.validateToken(tokenReqDto.getAccessToken())) {
            throw new IllegalArgumentException("로그아웃 : 유효하지 않은 토큰입니다.");
        }

        String accessToken = tokenReqDto.getAccessToken();
        JwtAuthenticationFilter.setBlacklist(accessToken);

        return accessToken;
    }

    public String getUserEmailFromJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        return user.getUsername();
    }


    public Long getUserIdFromJWT() {
        String email = getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다.")
        );
        return member.getId();
    }

    public Member getUserFromEmail(){
        String email = getUserEmailFromJwt();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        return member;
    }
}
