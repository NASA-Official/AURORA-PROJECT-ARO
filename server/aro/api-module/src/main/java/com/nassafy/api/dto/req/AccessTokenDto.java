package com.nassafy.api.dto.req;

import com.nassafy.core.DTO.ProviderType;
import lombok.Data;
@Data
public class AccessTokenDto {
    ProviderType providerType;

    private String accessToken;
}

