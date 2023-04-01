package com.nassafy.core.respository;

import com.nassafy.core.entity.Forecast;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ForecastRepository extends JpaRepository<Forecast, Integer> {

    Optional<Forecast> findByDateTime(LocalDateTime dateTime);
    List<Forecast> findAll(Sort sort);
}
