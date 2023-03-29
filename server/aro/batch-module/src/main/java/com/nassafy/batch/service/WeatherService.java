package com.nassafy.batch.service;

import com.nassafy.core.respository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job weatherJob;

//    @Transactional
    public void runWeatherJob() throws Exception {
        LOGGER.debug("runWeatherJob() is executed");

        weatherRepository.deleteAll();

        jobLauncher.run(weatherJob, new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters());
    }
}
