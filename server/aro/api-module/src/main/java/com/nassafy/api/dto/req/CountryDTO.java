package com.nassafy.api.dto.req;

import lombok.Getter;

@Getter
public class CountryDTO {
    private Long countryId;
    private String country;
    private String countryEmoji;

    public CountryDTO(Long countryId, String country, String countryEmoji) {
        this.countryId = countryId;
        this.country = country;
        this.countryEmoji = countryEmoji;
    }
}
