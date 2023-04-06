package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class InterestListDTO {
    public List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList;
    // 메테오 서비스를 하게 된다면 DTO 본격적으로 파야한다.
    public String memteorInterestOrNotDTO;

    @Builder
    public InterestListDTO(List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList, String memteorInterestOrNotDTO) {
        this.attractionInterestOrNotDTOList = attractionInterestOrNotDTOList;
        this.memteorInterestOrNotDTO = memteorInterestOrNotDTO;
    }
}
