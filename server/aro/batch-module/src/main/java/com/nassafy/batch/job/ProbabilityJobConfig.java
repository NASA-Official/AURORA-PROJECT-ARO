package com.nassafy.batch.job;

import com.nassafy.core.entity.Attraction;
import com.nassafy.core.entity.Forecast;
import com.nassafy.core.entity.Probability;
import com.nassafy.core.entity.Weather;
import com.nassafy.core.respository.AttractionRepository;
import com.nassafy.core.respository.ForecastRepository;
import com.nassafy.core.respository.ProbabilityRepository;
import com.nassafy.core.respository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.hql.internal.ast.tree.IdentNode;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProbabilityJobConfig {
    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ProbabilityRepository probabilityRepository;

    private final AttractionRepository attractionRepository;
    private final WeatherRepository weatherRepository;

    private final ForecastRepository forecastRepository;

    @Bean
    public Job probabilityJob(){
        log.info("************ This is ProbabilityJob");
        return jobBuilderFactory.get("probilityJob")
                .preventRestart()
                .start(probabilityStep())
                .build();
    }

    public Step probabilityStep(){
        log.info("************ This is probabilityStep");
        return stepBuilderFactory.get("probabilityStep")
                .tasklet(ProbabilityTasklet(null))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet ProbabilityTasklet(@Value("#{jobParameters[time]}") String time) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                log.info("*****************This is ProbabilityTasklet");
                probabilityRepository.deleteAllInBatch();

                // 지역을 불러온다
                List<Attraction> attractionList = attractionRepository.findAll();
                //  kp 불러온다. 날짜를 기준으로 정렬한다.
                List<Forecast> forecasts = forecastRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"));
                // 날씨를 불러온다. 시간을 기준으로 정렬한다.
                List<Weather> weatherList = weatherRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"));

                // 현재 시간을 원하는 시간으로 포맷팅 하루를 0,3,6,9,12,15,18,21 로 나누고 내림한다.
                LocalDateTime currentTime = LocalDateTime.now().minusHours(9);
                int hour = currentTime.getHour();
                int newHour = (hour % 3 == 0) ? hour : hour - (hour % 3);
                LocalDateTime startTime = currentTime.withHour(newHour).withMinute(0).withSecond(0).withNano(0);

                // 포문 돌리면서 데이터 저장 하면 될듯? 객체 부르고
                for (Forecast forcast : forecasts) {
                    LocalDateTime localDateTime = forcast.getDateTime();

                    Float kp = forcast.getKp();

                    for (Weather weather : weatherList) {
                        // weather 가 더 커지면 비교 할 필요가 없음으로 break
                        if (forcast.getDateTime().isBefore(weather.getDateTime())){
                            break;
                        }
                        if (!forcast.getDateTime().isEqual(weather.getDateTime())){
                            continue;
                        }
                        for (Attraction attraction : attractionList) {
                            Float lat = attraction.getLatitude();
                            Float lng = attraction.getLongitude();
                            if (lat.equals(weather.getLatitude()) && lng.equals(weather.getLongitude())){
                                // 확률 계산
                                Integer visibility = weather.getVisibility();
                                Integer cloud = weather.getClouds();
                                Integer prob = Math.round(kp * 10 + visibility / 2000 - (cloud - 50) * (1 / 25000));
                                Probability probability = new Probability(localDateTime, attraction, prob);
                                probabilityRepository.save(probability);
                                break;
                            }
                        }
                    }
                }
                return RepeatStatus.FINISHED;
            }
        };
    }
}
