package com.nassafy.api.dto.req;

import com.nassafy.core.DTO.MapStampDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class CollectionsDTO {
    public String mapImage;
    public List<MapStampDTO> mapStampsItem;

    public CollectionsDTO(String mapImage, List<MapStampDTO> mapStampDTOS) {
        this.mapImage = mapImage;
        this.mapStampsItem = mapStampDTOS;

    }
}
