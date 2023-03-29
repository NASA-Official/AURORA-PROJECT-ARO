package com.nassafy.core.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "weather_temp")
public class WeatherTemp extends Weather{
}
