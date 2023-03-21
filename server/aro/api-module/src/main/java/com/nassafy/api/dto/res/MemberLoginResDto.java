package com.nassafy.api.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberLoginResDto {
    private String email;
    private String nickname;
}
