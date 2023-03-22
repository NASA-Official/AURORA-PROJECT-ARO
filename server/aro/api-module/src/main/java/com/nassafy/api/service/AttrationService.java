package com.nassafy.api.service;

import com.nassafy.api.dto.req.MapAttractionDTO;
import com.nassafy.core.entity.Attraction;
import com.nassafy.core.respository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttrationService {
    private final AttractionRepository attractionRepository;

    public List<String> getAllNation(){
        return attractionRepository.findAllNation();
    }

    public List<Attraction> getAttractionByNation(String nation) {
        return attractionRepository.findByNation(nation);
    }


    /**
     *
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
}
