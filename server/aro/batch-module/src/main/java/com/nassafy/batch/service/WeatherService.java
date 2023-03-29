package com.nassafy.batch.service;

import com.nassafy.core.entity.Weather;
import com.nassafy.core.entity.WeatherTemp;
import com.nassafy.core.respository.WeatherRepository;
import com.nassafy.core.respository.WeatherTempRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private WeatherTempRepository weatherTempRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job weatherJob;

    @Transactional
    public void runWeatherJob() throws Exception {
        LOGGER.debug("runWeatherJob() is executed");

        // 임시 테이블의 모든 데이터를 삭제합니다.
        weatherTempRepository.deleteAll();

        jobLauncher.run(weatherJob, new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters());

        // 데이터 갱신 작업이 완료되면 원본 테이블과 임시 테이블을 교체
        weatherRepository.deleteAll();
        List<WeatherTemp> tempList = weatherTempRepository.findAll();
        List<Weather> newWeatherList = tempList.stream().map(weatherTemp -> {
            Weather weather = new Weather();
            BeanUtils.copyProperties(weatherTemp, weather);
            return weather;
        }).collect(Collectors.toList());
        weatherRepository.saveAll(newWeatherList);
    }
}
