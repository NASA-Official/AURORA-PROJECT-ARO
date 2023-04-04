package com.nassafy.api.service;

import com.nassafy.api.dto.res.CountryInterestDTO;
import com.nassafy.core.entity.Country;
import com.nassafy.core.entity.MeteorInterest;
import com.nassafy.core.respository.CountryRepository;
import com.nassafy.core.respository.MeteorInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    public final CountryRepository countryRepository;
    public final MeteorInterestRepository meteorInterestRepository;

    private final JwtService jwtService;

    /**
     * 81번 Api
     * @return List<CountryInterestDTO>
     */
    public List<CountryInterestDTO> getNations() {
        Long memberId = jwtService.getUserIdFromJWT();
        List<CountryInterestDTO> countryInterestDTOList = new ArrayList<>();
        List<Country> countryList = countryRepository.findAll();
        MeteorInterest meteorInterest = meteorInterestRepository.findByMemberId(memberId).orElse(null);

        for (Country country : countryList) {
            Boolean interest = false;
            if (meteorInterest != null && meteorInterest.getCountry().getCountry().equals(country.getCountry())) {
                interest = true;
            }
            countryInterestDTOList.add(
                    CountryInterestDTO.builder()
                            .countryId(country.getCountryId())
                            .country(country.getCountry())
                            .countryEmoji(country.getCountryEmoji())
                            .interest(interest)
                            .build()
            );
        }
        return countryInterestDTOList;
    }
}
