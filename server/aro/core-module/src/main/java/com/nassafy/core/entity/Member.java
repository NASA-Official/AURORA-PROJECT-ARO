package com.nassafy.core.entity;

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

    private boolean alarm;

    private boolean auroraDisplay;

    private boolean auroraService;
    @OneToMany(mappedBy = "member")
    private List<Interest> interests = new ArrayList<>();
}
