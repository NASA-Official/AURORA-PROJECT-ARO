package com.nassafy.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Interest> interests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Stamp> stamps = new ArrayList<>();

    public void toggleAlarm(){
        this.alarm = !this.alarm;
    }

    public void toggleAuroraDisplay(){
        this.auroraDisplay = !this.auroraDisplay;
    }

}

