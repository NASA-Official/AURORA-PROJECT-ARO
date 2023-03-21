package com.nassafy.core.DTO;


import lombok.Getter;

@Getter
public class ServiesRegisterDTO {
    private Boolean auroraServise;
    private Boolean meteorServise;

    public ServiesRegisterDTO(Boolean auroraServise, Boolean meteorServise){
        this.auroraServise = auroraServise;
        this.meteorServise = meteorServise;
    }
}
