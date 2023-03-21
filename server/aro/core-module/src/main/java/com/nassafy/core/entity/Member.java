package com.nassafy.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private boolean alarm = true;

    private boolean auroraDisplay = true;

    private boolean auroraService = false;

    private boolean meteorService = false;
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Stamp> stamps = new ArrayList<>();

    // 영속성 컨텍스트 비워주는 비지니스 메서드
    public void clearInterest(){
        this.interests = new ArrayList<>();
    }

    // 영속성 컨텍스트에 추가해주는 비지니스 메서드
    public void addInterest(){
        this.interests = interests;
    }

    public boolean getAlarm(){
        return this.alarm;
    }

    public boolean getAuroraDisplay(){
        return this.auroraDisplay;
    }

    public void toggleAlarm(){
        this.alarm = !this.alarm;
    }

    public void toggleAuroraDisplay(){
        this.auroraDisplay = !this.auroraDisplay;
    }

    public boolean getAuroraService(){
        return this.auroraService;
    }

    public boolean getMeteorService(){
        return this.meteorService;
    }

    public void toggleAuroraService(){
        this.auroraService = !this.auroraService;
    }

    public void toggleMeteorService(){
        this.meteorService = !this.meteorService;
    }

}

