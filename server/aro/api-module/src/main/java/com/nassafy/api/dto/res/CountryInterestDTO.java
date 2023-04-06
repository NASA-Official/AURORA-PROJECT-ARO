package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountryInterestDTO {
    private Long countryId;
    private String country;
    private String countryEmoji;
    private Boolean interest;

    public CountryInterestDTO(Long countryId, String country, String countryEmoji, Boolean interest) {
        this.countryId = countryId;
        this.country = country;
        this.countryEmoji = countryEmoji;
        this.interest = interest;
    }
}
