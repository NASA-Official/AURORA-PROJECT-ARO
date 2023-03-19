package com.nassafy.api.dto.req;

import lombok.Data;

@Data
public class MemberLoginReqDto {
    private String memberId;
    private String password;
}
