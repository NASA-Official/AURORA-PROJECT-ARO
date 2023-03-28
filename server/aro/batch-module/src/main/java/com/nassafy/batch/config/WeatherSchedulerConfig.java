package com.nassafy.batch.config;
import com.nassafy.core.respository.WeatherRepository;
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
@Configuration
@EnableScheduling
@EnableBatchProcessing
public class WeatherSchedulerConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherSchedulerConfig.class);

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job weatherJob;

//     3시간 마다 한번
//    @Scheduled(fixedRate = 3 * 60 * 60 * 1000)
    // 테스트 용
    @Scheduled(fixedRate = 60000)
    public void runWeatherJob() throws Exception {
        LOGGER.debug("runWeatherJob() is executed");
        weatherRepository.deleteAll();
        jobLauncher.run(weatherJob, new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters());
    }

}
