package com.nassafy.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Long id;

    private String nation;

    private String attractionName;

    private String description;

    private Float latitude;


    private Float longitude;

    private String colorStamp;

    // 작은 사진
    private String image;

    private String colorAuth;

    private String grayAuth;

    // 지도 사진
    private String mapImage;

    @OneToMany(mappedBy = "attraction")
    private List<Interest> interests = new ArrayList<>();
}
