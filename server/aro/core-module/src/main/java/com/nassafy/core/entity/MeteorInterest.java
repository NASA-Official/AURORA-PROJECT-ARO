package com.nassafy.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MeteorInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meteor_interest_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
