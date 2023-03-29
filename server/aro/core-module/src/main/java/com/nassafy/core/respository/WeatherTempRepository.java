package com.nassafy.core.respository;

import com.nassafy.core.entity.WeatherTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherTempRepository extends JpaRepository<WeatherTemp, Long> {
}

