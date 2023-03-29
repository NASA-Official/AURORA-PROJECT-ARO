package com.nassafy.batch.config;

import com.nassafy.batch.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableScheduling
@EnableBatchProcessing
public class WeatherSchedulerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherSchedulerConfig.class);

    @Autowired
    private WeatherService weatherService;

//    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Scheduled(cron = "0 */10 * * * *")
    public void runWeatherJob() throws Exception {
        LOGGER.debug("runWeatherJob() is executed");

        weatherService.runWeatherJob();
    }
}
