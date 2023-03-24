package com.nassafy.api.dto.req;

import com.nassafy.core.DTO.MapStampDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class CollectionsDTO {
    public List<MapStampDTO> mapStampDTOS;
    public String mapImage;

    public CollectionsDTO(List<MapStampDTO> mapStampDTOS, String mapImage) {
        this.mapStampDTOS = mapStampDTOS;
        this.mapImage = mapImage;
    }
}
