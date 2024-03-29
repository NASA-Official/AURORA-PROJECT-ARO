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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MeteorService {

    private final MeteorRepository meteorRepository;

    private final MeteorInterestRepository meteorInterestRepository;
    private final CountryRepository countryRepository;

    private final MemberRepository memberRepository;

    /**
     * 82번 Api
     * 유성우 서비스를 등록 X: BAD_REQUEST
     * 유성우 서비스를 등록 O && 관심 국가 등록 X: null -> Ok
     * 유성우 서비스를 등록 O && 관심 국가 등록 O: MeteorDTO
     * @return meteorDTO 국가명, List<유성우 명소 관련 정보>
     */
    public MeteorDTO getInterestMeteor(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found for member ID: " + memberId));

        MeteorInterest meteorInterest = meteorInterestRepository.findByMemberId(memberId)
                .orElse(null);

        if (meteorInterest == null) {
            // 유성우 서비스를 등록 O && 관심 국가 등록 X: null
            if (member.getMeteorService()) {
                log.warn("Meteor interest not found for member ID: " + memberId);
                return new MeteorDTO("", new ArrayList<>());
            }
            // 유성우 서비스를 등록 X: BAD_REQUEST
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please register the meteor service.");
            }
        }

        Country country = meteorInterest.getCountry();
        if (country == null) {
            throw new EntityNotFoundException("Country not found for meteor interest ID: " + meteorInterest.getId());
        }
        String countryName = country.getCountry();
        List<Meteor> meteorList = meteorRepository.findByNation(countryName);
        List<MeteorInformationDTO> meteorInformationDTOS = new ArrayList<>();
        for (Meteor meteor : meteorList) {
            String constellation = meteor.getConstellation();
            if (constellation == null) {
                log.warn("Constellation not found for meteor ID: " + meteor.getId());
                continue;
            }
            String constellationImage = "https://nassafy.s3.ap-northeast-2.amazonaws.com/%EB%B3%84%EC%9E%90%EB%A6%AC/" + constellation + "/icon.png";
            String detailImage = "https://nassafy.s3.ap-northeast-2.amazonaws.com/%EB%B3%84%EC%9E%90%EB%A6%AC/" + constellation + "/image.jpg";
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

        // 기존 관심있는 유성우를 삭제합니다.
        meteorInterestRepository.findByMemberId(memberId).ifPresent(meteorInterestRepository::delete);
        meteorInterestRepository.flush();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 멤버 ID입니다"));

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
