package com.nassafy.core.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_seq_generator")
    @SequenceGenerator(name = "weather_seq_generator", sequenceName = "weather_seq", allocationSize = 1)
    private Long id;
    private LocalDateTime dateTime;
    private Float latitude;
    private Float longitude;
    private String main;
    private Integer clouds;
}
