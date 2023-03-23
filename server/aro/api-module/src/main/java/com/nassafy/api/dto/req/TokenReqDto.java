package com.nassafy.api.dto.req;

import lombok.Getter;

@Getter
public class TokenReqDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
