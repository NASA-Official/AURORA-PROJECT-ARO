package com.nassafy.api.service;

import com.nassafy.core.respository.CountryRepository;
import com.nassafy.core.respository.MeteorInterestRepository;
import com.nassafy.core.respository.MeteorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeteorService {
    private final MeteorRepository meteorRepository;
    private final MeteorInterestRepository meteorInterestRepository;
    private final CountryRepository countryRepository;



}
