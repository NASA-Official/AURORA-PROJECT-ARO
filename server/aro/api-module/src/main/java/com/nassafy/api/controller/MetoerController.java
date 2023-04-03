package com.nassafy.api.controller;

import com.nassafy.api.service.MeteorService;
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
@RequestMapping("/api/meteor/")
@RequiredArgsConstructor
@Slf4j
public class MetoerController {
    private final MeteorService meteorService;

}
