package com.nassafy.api.dto.req;

import lombok.Data;

@Data
public class TokenReqDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
