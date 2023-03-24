package com.nassafy.api.dto.req;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ServiceDTO {
    public Boolean auroraService;
    public Boolean meteorService;

    @Builder
    public ServiceDTO(Boolean auroraService, Boolean meteorService) {
        this.auroraService = auroraService;
        this.meteorService = meteorService;
    }
}
