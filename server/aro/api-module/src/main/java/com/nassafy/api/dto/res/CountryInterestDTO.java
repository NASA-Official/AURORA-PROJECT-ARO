package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryInterestDTO {
    private Long countryId;
    private String countryName;
    private String countryImage;
    private Boolean interest;

    public CountryInterestDTO(Long countryId, String countryName, String countryImage, Boolean interest) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.countryImage = countryImage;
        this.interest = interest;
    }
}
