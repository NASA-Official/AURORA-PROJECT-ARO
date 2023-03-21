package com.nassafy.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "attraction")
    private List<Stamp> stamps = new ArrayList<>();

    @Builder
    public Attraction(String nation, String attractionName, String description, Float latitude, Float longitude, String colorStamp, String image, String colorAuth, String grayAuth, String mapImage) {
        this.nation = nation;
        this.attractionName = attractionName;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.colorStamp = colorStamp;
        this.image = image;
        this.colorAuth = colorAuth;
        this.grayAuth = grayAuth;
        this.mapImage = mapImage;
    }
}
