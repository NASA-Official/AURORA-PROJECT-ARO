package com.nassafy.api.service;

import com.nassafy.core.entity.Attraction;
import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.InterestRepository;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;
    private final MemberRepository memberRepository;
    private final AttractionRepository attractionRepository;

    @Transactional
    public void registerInterest(Long memberid, List<Long> attractionIds) {
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
    // 해당 회원 관심 지역 비우기
        member.getInterests().clear();



        // 새로운 관심 지역 등록
        for (Long attractionId : attractionIds) {
            Attraction attraction = attractionRepository.findById(attractionId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid attraction id"));
            Interest interest = new Interest();
            interest.setMember(member);
            interest.setAttraction(attraction);
            interestRepository.save(interest);
        }

    }

}
