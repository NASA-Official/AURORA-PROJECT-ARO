package com.nassafy.core.DTO;


import lombok.Getter;

@Getter
public class ServiesRegisterDTO {
    private boolean AuroraServise;
    private boolean MeteorServise;

    public ServiesRegisterDTO(boolean AuroraServise, boolean MeteorServise){
        this.AuroraServise = AuroraServise;
        this.MeteorServise = MeteorServise;
    }
}
