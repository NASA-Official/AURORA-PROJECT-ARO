package com.nassafy.batch.config;
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
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job weatherJob;

    // 3시간 마다 한번
    @Scheduled(fixedRate = 3 * 60 * 60 * 1000)
    public void runWeatherJob() throws Exception {
        jobLauncher.run(weatherJob, new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters());
    }
}
