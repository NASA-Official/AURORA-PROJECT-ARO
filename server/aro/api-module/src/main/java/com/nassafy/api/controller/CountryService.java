package com.nassafy.api.controller;

import com.nassafy.core.entity.Country;
import com.nassafy.core.respository.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Country/")
@RequiredArgsConstructor
@Slf4j
public class CountryController {
    private final CountryRepository countryRepository;
    // 80번 Api
    @GetMapping("signup")
    public ResponseEntity<List<Country>> getNationSignup(){
        List<Country> countryList = countryRepository.findAll();
        return ResponseEntity.ok(countryList);
    }

    // 81번 Api
    @GetMapping("")
    public ResponseEntity<List<CountryInterestDTO>> getNation(){
        List<CountryInterestDTO> countryInterestDTOS =
    }
}
