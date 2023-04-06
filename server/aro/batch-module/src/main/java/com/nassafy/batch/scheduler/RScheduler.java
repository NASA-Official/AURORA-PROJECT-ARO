package com.nassafy.batch.scheduler;

import com.nassafy.batch.config.WeatherSchedulerConfig;
import com.nassafy.batch.job.DailyPredictJobConfig;
import com.nassafy.batch.job.ThreeDaysPredictJopConfig;
import com.nassafy.batch.service.WeatherService;
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
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RScheduler {

    private final JobLauncher jobLauncher;

    private final ThreeDaysPredictJopConfig threeDaysPredictJopConfig;

    private final DailyPredictJobConfig dailyPredictJobConfig;

    private final WeatherService weatherService;

    private final ProbabilityScheduler probabilityScheduler;

    // batch 서버가 처음 올라갈 때 데이터 초기화 시키기 위해서
    @PostConstruct
    public void initialization() {
        log.info("*******************************This is initialization");
        // 3일치 데이터 넣기
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

        // 하루치 데이터 넣기
        try {
            jobLauncher.run(dailyPredictJobConfig.dailyPredictJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException |
                 org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
        // 날씨 데이터 실행
        try {
            weatherService.runWeatherJob(jobLauncher);
        } catch(Exception e) {
            log.error(e.getMessage());
        }

        // 확률 데이터 실행
        try {
            probabilityScheduler.runProbabilityJob();
            log.info("****************** 확률 데이터 실행");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    @Scheduled(cron = "0 0 */3 * * *") // 0 0 0 * * *(초, 분, 시, 일, 월, 요일) 0시 부터 3시간 단위로 실행
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

    @Scheduled(cron = "1 0 * * * *") // 0 0 * * * *(초, 분, 시, 일, 월, 요일) 매시간
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
