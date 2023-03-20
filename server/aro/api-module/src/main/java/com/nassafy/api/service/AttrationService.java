package com.nassafy.api.service;

import com.nassafy.core.entity.Attraction;
import com.nassafy.core.respository.AttractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
