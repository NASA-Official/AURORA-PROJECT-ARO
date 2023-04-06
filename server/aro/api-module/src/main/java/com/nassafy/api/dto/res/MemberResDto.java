package com.nassafy.api.dto.res;

import com.nassafy.core.entity.Member;
import lombok.Data;

@Data
public class MemberResDto {

    private Long id;

    private String email;

    private String password;

    private String nickname;

    private Boolean alarm;

    private boolean auroraDisplay;

    private boolean auroraService;

    private boolean meteorService;

    private String refreshToken;

    public void setMemberResDto(Member member){
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.alarm = member.getAlarm();
        this.auroraDisplay = member.getAuroraDisplay();
        this.auroraService = member.getAuroraService();
        this.meteorService = member.getMeteorService();
        this.refreshToken = member.getRefreshToken();
    }

}
