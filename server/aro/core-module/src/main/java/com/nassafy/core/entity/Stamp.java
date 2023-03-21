package com.nassafy.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stamp_id")
    private Long id;

    private Boolean certification = false;

    @Column(columnDefinition = "TEXT")
    private String memo;

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

    @OneToMany(mappedBy = "stamp")
    private List<StampImage> stampImages = new ArrayList<>();

    public void editMemo(String memo) {
        this.memo = memo;
    }
}
