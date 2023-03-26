package com.nassafy.api.service;

import com.nassafy.api.dto.req.CollectionsDTO;
import com.nassafy.api.dto.req.MapAttractionDTO;
import com.nassafy.core.DTO.MapStampDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.Stamp;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.StampRepository;
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
public class AttrationService {
    private final AttractionRepository attractionRepository;
    private final MemberRepository memberRepository;
    private final StampRepository stampRepository;
    private final JwtService jwtService;

    /**
     * 20번 Api
     * @return 모든 국가 명
     */
    public List<String> getAllNation(){
        return attractionRepository.findAllNation();
    }


    /**
     * 21번 Api
     * @return 지도 화면에 모든 명소 정보를 보내줌. 정보는 명소 id, 명소명, 명소 작은  사진, 명소 위도, 명소 경도
     */
    public List<MapAttractionDTO> getAttrationForMap() {
        List<Attraction> attractions = attractionRepository.findAll();
        List<MapAttractionDTO> mapAttractionDTOS = new ArrayList<>();
        for (Attraction attraction : attractions) {
            mapAttractionDTOS.add(new MapAttractionDTO(attraction.getId(), attraction.getAttractionName(), attraction.getMapImage(), attraction.getLatitude(), attraction.getLongitude()));
        }
        return mapAttractionDTOS;
    }


    /**
     * 22번 api
     * @param nation 국가 이름
     * @return 명소 아이디, 스탬프 이미지, 인증여부
     */
    public List<MapStampDTO> getStampsFormember(String nation) {
        Long memberId = jwtService.getUserIdFromJWT();
        List<Attraction> attractions = attractionRepository.findByNation(nation);
        List<Stamp> stamps = stampRepository.findByMemberId(memberId);
        List<MapStampDTO> mapStampDTOS = new ArrayList<>();

        for (Attraction attraction : attractions){
            for (Stamp stamp : stamps){
                if (attraction.getAttractionName().equals(stamp.getAttraction().getAttractionName())){
                    mapStampDTOS.add(new MapStampDTO(attraction.getId(), attraction.getColorStamp(), stamp.getCertification()));
                }
            }
        }
        return mapStampDTOS;
    }

    /**
     * 23번 Api
     * @param nationName 국가 명
     * @return MapImage
     */
    public String getMapImage(String nationName) {
        List<Attraction> attraction = attractionRepository.findByNation(nationName);
        String mapImage = attraction.get(0).getMapImage();
        return mapImage;
    }


    /**
     * 테스트
     * @param nation 국가명
     * @return 통짜 Api
     */
    public List<Attraction> getAttractionByNation(String nation) {
        return attractionRepository.findByNation(nation);
    }

    /**
     * 24번 Api
     * @param nation
     * @return 명소 개수 반환
     */
    public Integer getStampCountCountry(String nation) {
        List<Attraction> attractions = attractionRepository.findByNation(nation);
        return attractions.size();
    }



    /**
     * 25번 Api
     * @param nation 나라 이름
     * @return 유저가 모은 스탬프 개수 (나라별)
     */

    public Integer getMyStampCountCountry(String nation) {
        Long memberId = jwtService.getUserIdFromJWT();
        List<Attraction> attractions = attractionRepository.findByNation(nation);
        Integer MyCount = 0;
        for (Attraction attraction : attractions) {
            Long attractonId = attraction.getId();
            Stamp stamp = stampRepository.findByAttractionIdAndMemberId(attractonId, memberId).orElse(null);
            if (stamp.getCertification()){
                MyCount++;
            }
        }


        return MyCount;
    }

    /**
     * 26번 api
     * @param nation 국가 명
     * @return 스탬프리스트(명소id, 스탬프 이미지, 인증 유무) mapImage 
     */

    public CollectionsDTO getCollections(String nation) {
        List<MapStampDTO> mapStampDTO = getStampsFormember(nation);
        String mapImage = getMapImage(nation);
        CollectionsDTO collectionsDTO = new CollectionsDTO(mapImage, mapStampDTO);
        return collectionsDTO;
    }
}
