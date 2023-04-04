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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeteorService {
    private final MeteorRepository meteorRepository;
    private final MeteorInterestRepository meteorInterestRepository;
    private final MemberRepository memberRepository;

    private final CountryRepository countryRepository;
    private final JwtService jwtService;

    /**
     * 82번 Api
     * @return meteorDTO 국가명, List<유성우 명소 관련 정보>
     */
    public MeteorDTO getInterestMeteor() {
        Long memberId = jwtService.getUserIdFromJWT();
        MeteorInterest meteorInterest = meteorInterestRepository.findByMemberId(memberId).get();
        String countryName = meteorInterest.getCountry().getCountry();
        List<Meteor> meteorList = meteorRepository.findByNation(countryName);
        List<MeteorInformationDTO> meteorInformationDTOS = new ArrayList<>();
        for (Meteor meteor : meteorList) {
            String constellationImage = "https://nassafy.s3.ap-northeast-2.amazonaws.com/%EB%B3%84%EC%9E%90%EB%A6%AC/" + meteor.getConstellation() + "/icon.png";
            // 이미지 url 들어오면 바꿔야함
            String detailImage = "https://nassafy.s3.ap-northeast-2.amazonaws.com/%EB%B3%84%EC%9E%90%EB%A6%AC/" + meteor.getConstellation() + "/icon.png";
            meteorInformationDTOS.add(
                    MeteorInformationDTO.builder()
                            .meteorName(meteor.getMeteorName())
                            .meteorOriginalName(meteor.getMeteorName())
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

        if (countryId != null) {
            Country newCountry = countryRepository.findById(countryId)
                    .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 국가 ID입니다"));

            MeteorInterest meteorInterest = meteorInterestRepository.findByMemberId(memberId)
                    .orElseGet(() -> {
                        MeteorInterest newMeteorInterest = new MeteorInterest();
                        newMeteorInterest.setMember(member);
                        return newMeteorInterest;
                    });

            meteorInterest.setCountry(newCountry);
            meteorInterestRepository.save(meteorInterest);
        } else {
            meteorInterestRepository.findByMemberId(memberId).ifPresent(meteorInterestRepository::delete);
        }
    }



}
