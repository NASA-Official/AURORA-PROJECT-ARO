package com.nassafy.core.respository;


import com.nassafy.core.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM WeatherTemp")
    void deleteAllTemp();
}
