package com.nassafy.api.service;

import com.nassafy.api.dto.res.MeteorDTO;
import com.nassafy.api.dto.res.MeteorInformationDTO;
import com.nassafy.core.entity.Country;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.Meteor;
import com.nassafy.core.entity.MeteorInterest;
import com.nassafy.core.respository.CountryRepository;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.MeteorInterestRepository;
import com.nassafy.core.respository.MeteorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MeteorService {
    @Autowired
    private final MeteorRepository meteorRepository;
    @Autowired
    private final MeteorInterestRepository meteorInterestRepository;

    private final CountryRepository countryRepository;

    private final MemberRepository memberRepository;


    private final JwtService jwtService;

    /**
     * 82번 Api
     * @return meteorDTO 국가명, List<유성우 명소 관련 정보>
     */
    public MeteorDTO getInterestMeteor() {
        Long memberId = jwtService.getUserIdFromJWT();
        MeteorInterest meteorInterest = meteorInterestRepository.findByMemberId(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Meteor interest not found for member ID: " + memberId));
        log.info("***********************************************" + meteorInterest);
        String countryName = meteorInterest.getCountry().getCountry();
        log.info("***********************************************" + countryName);
        List<Meteor> meteorList = meteorRepository.findByNation(countryName);
        log.info("***********************************************" + meteorList);
        List<MeteorInformationDTO> meteorInformationDTOS = new ArrayList<>();
        for (Meteor meteor : meteorList) {
            String constellationImage = "https://nassafy.s3.ap-northeast-2.amazonaws.com/%EB%B3%84%EC%9E%90%EB%A6%AC/" + meteor.getConstellation() + "/icon.png";
            // 이미지 url 들어오면 바꿔야함
            String detailImage = "https://nassafy.s3.ap-northeast-2.amazonaws.com/%EB%B3%84%EC%9E%90%EB%A6%AC/" + meteor.getConstellation() + "/image.jpg";
            meteorInformationDTOS.add(
                    MeteorInformationDTO.builder()
                            .meteorName(meteor.getMeteorName())
                            .meteorOriginalName(meteor.getMeteorOriginalName())
                            .predictDate(meteor.getPredictDate())
                            .constellationImage(constellationImage)
                            .detailImage(detailImage)
                            .build()
            );
        }
        return new MeteorDTO(countryName, meteorInformationDTOS);
    }


    /**
     * 83번 Api
     * 관심 유성우 국가 등록, 만약 null 이 올 경우 값을 통째로 삭제한다.
     * @param memberId 유저ID
     * @param countryId 국가ID
     */
    public void postInterestMeteor(Long memberId, Long countryId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 멤버 ID입니다"));

        // 기존 관심있는 유성우를 삭제합니다.
        meteorInterestRepository.findByMemberId(memberId).ifPresent(meteorInterestRepository::delete);
        meteorInterestRepository.flush();
        // 새로운 관심있는 유성우를 등록합니다.
        if (countryId != null) {
            Country newCountry = countryRepository.findById(countryId)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 국가 ID입니다"));

            MeteorInterest meteorInterest = new MeteorInterest();
            meteorInterest.setMember(member);
            meteorInterest.setCountry(newCountry);
            meteorInterestRepository.save(meteorInterest);
        }
    }
}
