package com.nassafy.core.entity;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate // 이거 공부하자~
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private Float latitude;
    private Float longitude;
    private String main;
    private Integer clouds;
    private Integer visibility;

    @Builder
    public Weather(LocalDateTime dateTime, Float latitude, Float longitude, String main, Integer clouds, Integer visibility) {
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.main = main;
        this.clouds = clouds;
        this.visibility = visibility;
    }
}
