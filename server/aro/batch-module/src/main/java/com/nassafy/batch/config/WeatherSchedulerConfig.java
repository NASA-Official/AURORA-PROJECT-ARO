package com.nassafy.batch.config;
import com.nassafy.batch.service.WeatherService;
import com.nassafy.core.respository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Configuration
public class WeatherSchedulerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherSchedulerConfig.class);

    private final JobLauncher jobLauncher;
    @Autowired
    private WeatherService weatherService;
    // 최초 실행 시 실행
//    @PostConstruct
//    public void init() throws Exception {
//        runWeatherJob();
//    }
    @Scheduled(cron = "2 0 */3 * * *")
    public void runWeatherJob() throws Exception {
        LOGGER.debug("runWeatherJob() is executed");
        weatherService.runWeatherJob(jobLauncher);
    }

}
