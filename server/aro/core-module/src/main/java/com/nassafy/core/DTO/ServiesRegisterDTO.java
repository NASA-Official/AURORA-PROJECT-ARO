package com.nassafy.core.DTO;


import lombok.Getter;

@Getter
public class ServiesRegisterDTO {
    private boolean auroraServise;
    private boolean meteorServise;

    public ServiesRegisterDTO(boolean auroraServise, boolean meteorServise){
        this.auroraServise = auroraServise;
        this.meteorServise = meteorServise;
    }
}
