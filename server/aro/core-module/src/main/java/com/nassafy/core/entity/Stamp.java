package com.nassafy.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stamp_id")
    private Long id;

    private Boolean certification = false;

    private LocalDate certificationDate = null;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @OneToMany(mappedBy ="stamp", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<StampImage> stampImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    @Builder
    public Stamp(Boolean certification, String memo, Member member, Attraction attraction) {
        this.certification = certification;
        this.memo = memo;
        this.member = member;
        this.attraction = attraction;
    }

    public void editMemo(String memo) {
        this.memo = memo;
    }

    public void updateCertification(LocalDate certificationDate) {
        this.certificationDate = certificationDate;
        this.certification = true;
    }
}
