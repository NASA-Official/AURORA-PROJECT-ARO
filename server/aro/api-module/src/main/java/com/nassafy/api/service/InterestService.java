package com.nassafy.api.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.kms.model.NotFoundException;
import com.nassafy.api.dto.req.AttractionInterest;
import com.nassafy.api.dto.req.AttractionInterestOrNotDTO;
import com.nassafy.api.dto.req.InterestListDTO;
import com.nassafy.api.dto.res.InterestProbabiliyResDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.Probability;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.InterestRepository;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.ProbabilityRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    private final ProbabilityRepository probabilityRepository;

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
        if(attractionIds != null) {
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
    }


    /**
     * 42번 Api
     * @param nationName
     * @param memberId
     * @return
     */
    public List<AttractionInterest> getAttractionInterest(String nationName, Long memberId) {
        List<Attraction> attractionList = attractionRepository.findAll();
        List<Interest> interestList = interestRepository.findAllByMemberId(memberId)
                .orElse(new ArrayList<>());
        List<AttractionInterest> attractionInterestList = new ArrayList<>();
        for (Attraction attraction : attractionList) {
            if (!attraction.getNation().equals(nationName)) {
                continue;
            }
            boolean isInterest = false;
            for (Interest interest : interestList) {
                if (attraction.getId().equals(interest.getAttraction().getId())) {
                    isInterest = true;
                    break;
                }
            }
            AttractionInterest dto = new AttractionInterest(
                    attraction.getId(),
                    attraction.getColorStamp(),
                    attraction.getAttractionName(),
                    attraction.getDescription(),
                    isInterest
            );
            attractionInterestList.add(dto);
        }
        return attractionInterestList;
    }


    /**
     * 43번 Api
     * @param memberId
     * @return 리스트(관심지역Id, 명소Id, 스탬프, 명소명, 명소설명, 관심여부) + 메테오 더미 데이터 (서비스 추가시 수정 필요)
     */
    public InterestListDTO getInterest(Long memberId) {
        List<Interest> interestList = interestRepository.findAllByMemberId(memberId)
                .orElse(new ArrayList<>());
        List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList = new ArrayList<>();
        for (Interest interest : interestList) {
            Attraction attraction = interest.getAttraction();
            attractionInterestOrNotDTOList.add(new AttractionInterestOrNotDTO(interest.getId(), attraction.getId(), attraction.getColorStamp(), attraction.getAttractionName(), attraction.getDescription(), true));
        }
        InterestListDTO interestListDTO = new InterestListDTO(attractionInterestOrNotDTOList, "테스트");
        return interestListDTO;


    }

    /**
     * 11번 Api 관심 오로라 명소 지우기 로직
     * @param memberId
     */
    public void deleteAuroaAttraction(Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        // 해당 회원 관심 지역 비우기
        List<Interest> interests = member.getInterests();
        if(interests != null) {
            for (Interest interest : interests) {
                interestRepository.delete(interest);
            }
        }
        // 영속성 컨텍스트 비우기
        member.clearInterest();
    }

    public InterestProbabiliyResDTO getProbability(Long attractionId, LocalDateTime dateTime) {
        Attraction attraction = attractionRepository.findById(attractionId).orElseThrow(IllegalArgumentException::new);
        Probability probability = probabilityRepository.findByAttractionIdAndDateTime(attractionId, dateTime).orElseThrow(IllegalArgumentException::new);

        return InterestProbabiliyResDTO.builder().image(attraction.getImage()).prob(probability.getProb()).build();
    }
}
