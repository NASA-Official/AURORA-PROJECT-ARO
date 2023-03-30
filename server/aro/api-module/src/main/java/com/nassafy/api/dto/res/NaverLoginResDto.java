package com.nassafy.api.dto.res;

import com.nassafy.core.DTO.ProviderType;
import lombok.Data;

@Data
public class NaverLoginResDto {
    ProviderType providerType;
    String email;
    boolean isSignup;
}
