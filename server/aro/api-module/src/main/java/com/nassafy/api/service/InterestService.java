package com.nassafy.api.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.nassafy.api.dto.req.AttractionInterestOrNotDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.InterestRepository;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;
    private final MemberRepository memberRepository;
    private final AttractionRepository attractionRepository;


    @Transactional(readOnly = false)
    public void registerInterest(Long memberId, List<Long> attractionIds) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 멤버 ID입니다"));

        // 해당 회원 관심 지역 비우기
        List<Interest> interests = member.getInterests();
        for (Interest interest : interests) {
            interestRepository.delete(interest);
        }

        // 영속성 컨텍스트에 반영
        member.clearInterest();

        // 새로운 관심 지역 등록
        for (Long attractionId : attractionIds) {
            Attraction attraction = attractionRepository.findById(attractionId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid attraction id"));
            Interest interest = new Interest();
            interest.setMember(member);
            interest.setAttraction(attraction);
            interestRepository.save(interest);
            member.getInterests().add(interest);
        }
    }

    public List<AttractionInterestOrNotDTO> getAttractionInterestOrNot(String nationName, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 없습니다."));

        List<Attraction> attractionList = attractionRepository.findAll();
        System.out.println(attractionList);
        List<Interest> interestList = interestRepository.findAllByMemberId(memberId)
                .orElse(new ArrayList<>());
        System.out.println(interestList);
        List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList = new ArrayList<>();
        for (Attraction attraction : attractionList) {
            if (!attraction.getNation().equals(nationName)) {
                continue;
            }
            boolean isInterest = false;
            for (Interest interest : interestList) {
                System.out.println(attraction.getId().equals(interest.getAttraction().getId()));
                if (attraction.getId().equals(interest.getAttraction().getId())) {
                    isInterest = true;
                    break;
                }
            }
            AttractionInterestOrNotDTO dto = new AttractionInterestOrNotDTO(
                    attraction.getId(),
                    attraction.getColorStamp(),
                    attraction.getAttractionName(),
                    attraction.getDescription(),
                    isInterest
            );
            System.out.println(dto);
            attractionInterestOrNotDTOList.add(dto);
        }
        return attractionInterestOrNotDTOList;
    }

}