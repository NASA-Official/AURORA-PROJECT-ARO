package com.nassafy.api.dto.res;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MeteorDTO {
    private String nation;
    private List<MeteorInformationDTO> meteorList;

    public MeteorDTO(String nation, List<MeteorInformationDTO> meteorList) {
        this.nation = nation;
        this.meteorList = meteorList;
    }
}
