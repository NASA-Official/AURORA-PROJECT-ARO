package com.nassafy.api.dto.req;

import com.nassafy.core.DTO.ProviderType;
import lombok.Data;

@Data
public class MemberLoginReqDto {
    ProviderType providerType;
    private String email;
    private String password;
}
