package com.nassafy.api.service;

import com.nassafy.api.dto.req.SingupAttractionDTO;
import com.nassafy.api.dto.req.StampDiaryReqDTO;
import com.nassafy.api.dto.res.StampDiaryResDTO;
import com.nassafy.api.util.S3Util;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.DTO.RegisterStampDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.Stamp;
import com.nassafy.core.entity.StampImage;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.StampImageRepository;
import com.nassafy.core.respository.StampRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StampService {
    private static final Logger logger = LoggerFactory.getLogger(StampService.class);
    @Autowired
    private StampRepository stampRepository;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StampImageRepository stampImageRepository;

    @Autowired
    private S3Util s3Util;

    /**
     * 회원 가입 시 명소 리스트 제공
     * @param countryName 국가 명
     * @return 명소 id, 명소 명, 스탬프 이미지, 명소 설명
     */
    public List<SingupAttractionDTO> findStampsCountry(String countryName) {
        List<Attraction> attractions = attractionRepository.findByNation(countryName);
        List<SingupAttractionDTO> singupAttractionDTOS = new ArrayList<>();

        for (Attraction attraction : attractions) {
            singupAttractionDTOS.add(new SingupAttractionDTO(attraction.getId(), attraction.getAttractionName(), attraction.getColorStamp() ,attraction.getDescription()));
        }
        return singupAttractionDTOS;
    }

    public List<MapStampDTO> findStampsByUserAndCountry(Long userId, String countryName) {
        List<Stamp> stamps = stampRepository.findByMemberId(userId);
        List<Attraction> attractions = attractionRepository.findByNation(countryName);

        List<MapStampDTO> mapStamps = new ArrayList<>();
        for (Attraction attraction : attractions) {
            Stamp stamp = stamps.stream()
                    .filter(s -> s.getAttraction().getId().equals(attraction.getId()))
                    .findFirst()
                    .orElse(null);

            if (stamp != null) {
                mapStamps.add(new MapStampDTO(attraction.getColorStamp(), stamp.getCertification()));
            }
        }

        return mapStamps;
    }

    // 자동으로 데이터를 추가하는 로직 회원 가입 이후 로직에서 호출 됨
    @Transactional
    public Integer makeStamp(String memberEmail){
        Member member = memberRepository.findByEmail(memberEmail).
                orElseThrow(
                        () -> new EntityNotFoundException("회원이 없습니다.")
                );
        List<Attraction> attractions = attractionRepository.findAll();
        for (Attraction attraction : attractions){

            Stamp stamp = Stamp.builder()
                    .member(member)
                    .attraction(attraction)
                    .certification(false)
                    .memo("")
                    .build();
            stampRepository.save(stamp);
        }
        return attractions.size();
    }

    public void createStampDiary(String nation, String attraction, Long memberId, StampDiaryReqDTO stampDiaryReqDTO) throws IllegalArgumentException, IOException {

        log.info("start create stamp diary service");

        Stamp stamp = stampRepository
                .findByAttraction_attractionNameAndMemberId(attraction, memberId)
                .orElseThrow(IllegalArgumentException::new);

        log.info("find stamp success");

        stamp.editMemo(stampDiaryReqDTO.getMemo());

        Stamp savedStamp = stampRepository.save(stamp);

        log.info("image upload start");
        for (MultipartFile file: stampDiaryReqDTO.getFiles()) {
            String imageUrl = s3Util.upload(file, "diary/" + memberId.toString() + "/" + nation + "/" + attraction);

            StampImage stampImage = StampImage.builder().image(imageUrl).stamp(savedStamp).build();

            stampImageRepository.save(stampImage);
        }
        log.info("image upload stop");
    }

    public StampDiaryResDTO getStampDiary(String nation, String attractionName, Long memberId) {
        Stamp stamp = stampRepository
                .findByAttraction_attractionNameAndMemberId(attractionName, memberId)
                .orElseThrow(IllegalArgumentException::new);
        List<String> stampImages = stampImageRepository.findByStampId(stamp.getId()).stream().map(StampImage::getImage).collect(Collectors.toList());
        Attraction attraction = attractionRepository.findById(stamp.getAttraction().getId()).orElseThrow(IllegalArgumentException::new);

        return StampDiaryResDTO.builder()
                .images(stampImages).memo(stamp.getMemo())
                .attractionName(attractionName)
                .description(attraction.getDescription())
                .nation(nation)
                .build();
    }

    public void editStampDiary(String nation, String attractionName, Long memberId,
                               List<MultipartFile> newImageLists, List<String> deleteImageLists, String memo)
            throws IOException {
        Stamp stamp = stampRepository
                .findByAttraction_attractionNameAndMemberId(attractionName, memberId)
                .orElseThrow(IllegalArgumentException::new);

        // memo에 변경 사항이 있는 경우 memo 수정
        if (memo != null) {
            stamp.editMemo(memo);
            stampRepository.save(stamp);
        }

        // 삭제 요청이 들어온 이미지 삭제하기
        int deleteCnt = 0;
        for (String url: deleteImageLists) {
            String result = s3Util.delete(url.substring(48));  // s3에서 이미지 삭제

            if (result.equals("success")) {
                deleteCnt += 1;
            }

            StampImage stampImage = stampImageRepository.findByImage(url).orElseThrow(IllegalArgumentException::new);
            stampImageRepository.delete(stampImage);
        }

        log.debug(String.valueOf(deleteCnt) + "개의 사진이 삭제되었습니다.");

        // 추가된 이미지 저장하기
        for (MultipartFile file: newImageLists) {
            String imageUrl = s3Util.upload(file, "diary/" + memberId.toString() + "/" + nation + "/" + attractionName);

            StampImage stampImage = StampImage.builder().image(imageUrl).stamp(stamp).build();

            stampImageRepository.save(stampImage);
        }
    }
}
