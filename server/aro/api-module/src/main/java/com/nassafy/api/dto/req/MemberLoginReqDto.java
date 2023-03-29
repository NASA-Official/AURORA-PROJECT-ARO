package com.nassafy.api.dto.req;

import com.nassafy.core.entity.ProviderType;
import lombok.Data;

@Data
public class MemberLoginReqDto {

    private ProviderType providerType;
    private String email;
    private String password;
}
