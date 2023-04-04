package com.nassafy.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Meteor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meteor_id")
    private Long id;

    private String nation;

    private String meteorName;

    private String meteorOriginalName;

    private String predictDate;

    private String constellation;

}

