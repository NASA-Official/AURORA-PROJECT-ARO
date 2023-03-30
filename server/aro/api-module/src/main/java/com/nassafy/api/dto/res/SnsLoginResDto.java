package com.nassafy.api.dto.res;

import com.nassafy.core.DTO.ProviderType;
import lombok.Data;

@Data
public class SnsLoginResDto {
    ProviderType providerType;
    String email;
    boolean isSignup;
}
