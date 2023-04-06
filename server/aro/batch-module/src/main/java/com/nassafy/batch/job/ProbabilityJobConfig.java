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

import java.time.Duration;
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


                List<Attraction> attractionList = attractionRepository.findAll();
                //  kp 불러온다. 날짜를 기준으로 정렬한다.
                List<Forecast> forecasts = forecastRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"));
                // 날씨를 불러온다. 시간을 기준으로 정렬한다.
                List<Weather> weatherList = weatherRepository.findAll(Sort.by(Sort.Direction.ASC, "dateTime"));


                // 포문 돌리면서 데이터 forecast 는 1시간 단위로 데이터가 저장 되어있고 weather은 3시간 단위로 데이터가 저장되어있다.
                // weather의 시간 데이터 범위가 forecast의 시간 데이터 범위 보다 크다
                for (Forecast forecast : forecasts) {
                    LocalDateTime forecastDateTime = forecast.getDateTime();

                    Float kp = forecast.getKp();

                    for (Weather weather : weatherList) {
                        LocalDateTime weatherDateTime = weather.getDateTime().minusHours(1);

                        // weather의 시간이 forecast의 시간보다 3시간 이상 크면 break
                        if (weatherDateTime.isAfter(forecastDateTime.plusHours(3))) {
                            break;
                        }

                        // weather의 시간이 forecast의 시간보다 같거나 2시간 이하로 작을 때만 계산 및 저장
                        if (!weatherDateTime.isBefore(forecastDateTime) && weatherDateTime.isBefore(forecastDateTime.plusHours(3))) {
                            for (Attraction attraction : attractionList) {
                                Float lat = attraction.getLatitude();
                                Float lng = attraction.getLongitude();
                                if (lat.equals(weather.getLatitude()) && lng.equals(weather.getLongitude())) {
                                    // 확률 계산
                                    Integer visibility = weather.getVisibility();
                                    Integer cloud = weather.getClouds();
                                    Integer prob = (int) Math.round(kp * 10 + visibility / 2000 - Math.pow((cloud - 50), 3) * (1.0 / 25000));
                                    Probability probability = new Probability(forecastDateTime, attraction, prob);
                                    probabilityRepository.save(probability);
                                    break;
                                }
                            }
                        }
                    }
                    //
                }

                return RepeatStatus.FINISHED;
            }
        };
    }
}
