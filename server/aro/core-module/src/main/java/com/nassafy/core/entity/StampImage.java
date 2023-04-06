package com.nassafy.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class StampImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stamp_id")
    private Stamp stamp;

    private String image;

    @Builder
    public StampImage(Stamp stamp, String image) {
        this.stamp = stamp;
        this.image = image;
    }
}
