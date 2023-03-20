package com.nassafy.api.dto.req;

import lombok.Data;

@Data
public class MemberLoginReqDto {
    private String email;
    private String password;
}
