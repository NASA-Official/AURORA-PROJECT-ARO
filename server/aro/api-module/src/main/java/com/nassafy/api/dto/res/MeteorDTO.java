package com.nassafy.api.dto.res;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MeteorDTO {
    private String nation;
    private List<MeteorInformationDTO> meteorList;

    public MeteorDTO(String nation, List<MeteorInformationDTO> meteorList) {
        this.nation = nation;
        this.meteorList = meteorList;
    }
}
