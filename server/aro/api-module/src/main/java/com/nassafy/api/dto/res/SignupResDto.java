package com.nassafy.api.dto.res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupResDto {

    // 유저정보
    private String email;
    private String password;
    private String nickname;

    // 서비스 선택 여부
    private boolean auroraService;
    private boolean meteorService;

}
