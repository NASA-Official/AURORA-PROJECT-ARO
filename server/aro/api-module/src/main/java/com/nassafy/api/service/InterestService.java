package com.nassafy.api.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.nassafy.api.dto.req.AttractionInterestOrNotDTO;
import com.nassafy.api.dto.req.InterestListDTO;
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

    /**
     * 41번 Api
     * @param memberId
     * @param attractionIds
     */
    @Transactional(readOnly = false)
    public void registerInterest(Long memberId, List<Long> attractionIds) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 멤버 ID입니다"));

        // 해당 회원 관심 지역 비우기
        List<Interest> interests = member.getInterests();
        if(interests != null) {
            for (Interest interest : interests) {
                interestRepository.delete(interest);
            }
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


    /**
     * 42번 Api
     * @param nationName
     * @param memberId
     * @return
     */
    public List<AttractionInterestOrNotDTO> getAttractionInterestOrNot(String nationName, Long memberId) {
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


    /**
     * 43번 Api
     * @param memberId
     * @return 리스트(명소Id, 스탬프, 명소명, 명소설명, 관심여부) + 메테오 더미 데이터 (서비스 추가시 수정 필요)
     */
    public InterestListDTO getInterest(Long memberId) {
        List<Interest> interestList = interestRepository.findAllByMemberId(memberId)
                .orElse(new ArrayList<>());
        List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList = new ArrayList<>();
        for (Interest interest : interestList) {
            Attraction attraction = interest.getAttraction();
            attractionInterestOrNotDTOList.add(new AttractionInterestOrNotDTO(attraction.getId(), attraction.getColorStamp(), attraction.getAttractionName(), attraction.getDescription(), true));
        }
        InterestListDTO interestListDTO = new InterestListDTO(attractionInterestOrNotDTOList, "테스트");
        return interestListDTO;

    }
}
