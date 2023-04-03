package com.nassafy.api.dto.req;


import com.nassafy.core.DTO.ProviderType;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SignupReqDto {

    ProviderType providerType;

    // 유저정보
    private String email;
    private String password;
    private String nickname;

    // 서비스 선택 여부
    private boolean auroraService;
    private boolean meteorService;

    // 서비스별 선택한 명소 리스트
    private List<Long> auroraPlaces;
    private Long countryId;
//
//    public SignupReqDto(){
//        auroraPlaces = new LinkedList<>();
//        meteorPlaces = new LinkedList<>();
//    }

//    @Override
//    public String toString() {
//        return "UserVo [email=" + email + ", password=" + password + ", nickname=" + nickname +
//                ", auroraService=" + auroraService + ", meteorService=" + meteorService +"]";
//    }
}
