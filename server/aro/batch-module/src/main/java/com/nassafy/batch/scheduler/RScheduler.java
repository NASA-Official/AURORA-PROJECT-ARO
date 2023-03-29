package com.nassafy.batch.scheduler;

import com.nassafy.batch.job.DailyPredictJobConfig;
import com.nassafy.batch.job.ThreeDaysPredictJopConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RScheduler {

    private final JobLauncher jobLauncher;

    private final ThreeDaysPredictJopConfig threeDaysPredictJopConfig;

    private final DailyPredictJobConfig dailyPredictJobConfig;


    @Scheduled(cron = "0 * * * * *") // 0 0 0 * * *(초, 분, 시, 일, 월, 요일) 매일
    public void runThreeDaysPredictJob() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            jobLauncher.run(threeDaysPredictJopConfig.threeDaysPredictJop(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "0 * * * * *") // 0 0 * * * *(초, 분, 시, 일, 월, 요일) 매시간
    public void runDailyPredictJob() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            jobLauncher.run(dailyPredictJobConfig.dailyPredictJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }

}
