package com.nassafy.api.service;

import com.nassafy.core.entity.Country;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.MeteorInterest;
import com.nassafy.core.respository.CountryRepository;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.MeteorInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MeteorInterestService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MeteorInterestRepository meteorInterestRepository;

    @Autowired
    private CountryRepository countryRepository;

    // 회원 가입 때 사용되는 로직
    @Transactional
    public Long makeMeteorInterestNation(String memberEmail, Long countryId) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new EntityNotFoundException("회원이 없습니다."));
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("국가를 찾을 수 없습니다."));

        MeteorInterest meteorInterest = new MeteorInterest();
        meteorInterest.setMember(member);
        meteorInterest.setCountry(country);

        MeteorInterest savedMeteorInterest = meteorInterestRepository.save(meteorInterest);
        return savedMeteorInterest.getId();
    }

}
