package com.nassafy.batch.job;

import com.nassafy.core.entity.Forecast;
import com.nassafy.core.respository.ForecastRepository;
import com.nassafy.core.respository.RedisDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ThreeDaysPredictJopConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ForecastRepository forecastRepository;

    private final RedisDAO redisDAO;

    @Bean
    public Job threeDaysPredictJop() {
        log.info("********** This is ThreeDaysPredictJop");

        return jobBuilderFactory.get("threeDaysPredictJop")
                .preventRestart()
                .start(threeDaysPredictStep())
                .build();
    }

    public Step threeDaysPredictStep() {
        log.info("********** This is ThreeDaysPredictStep");
        return stepBuilderFactory.get("threeDaysPredictStep")
                .tasklet(threeDaysPredictTasklet(null))
                .build();
    }



    /*
     * 해당 tasklet은 db에 데이터를 넣기 위한 사전작업을 하는 부분으로
     * 0. R에서 분석 데이터 가지고 오기
     * 1. 기존에 존재하던 데이터 싹 밀어버리기(delete)
     * 2. 새로운 데이터 삽입
     */
    @Bean
    @StepScope
    public Tasklet threeDaysPredictTasklet(@Value("#{jobParameters[time]}") String time) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                RConnection conn = null;
                try {
                    conn = new RConnection("rstudio", 6311);  // // 로컬에서는 host를 j8d106.p.ssafy.io로, 코드 올릴 때는 rstudio로 변경하기

                    // 현재 시간으로부터 1달 전 날짜 계산
                    LocalDateTime now = LocalDateTime.now();
                    now = now.minusMonths(1);
                    String input_date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();


                    // R script를 실행해서 결과 받아오기
                    conn.eval("source('/home/rstudio/threeDaysPredict.R')");
                    conn.eval("kp_data <- crawlingKPData('" + input_date +"')");
                    RList rList = conn.eval("kp_data").asList();
                    double[] predictKp = conn.eval("predictThreeDayKP(kp_data)").asDoubles();

                    log.info("R script 실행 완료");

                    // 마지막 시간 받아오기
                    int[] years = rList.at("YEAR").asIntegers();
                    int[] months = rList.at("MONTH").asIntegers();
                    int[] days = rList.at("DAY").asIntegers();
                    int[] times = rList.at("TIME").asIntegers();

                    int year = years[years.length - 1];
                    int month = months[months.length - 1];
                    int day = days[days.length - 1];
                    int time = times[times.length - 1] == 0 ? times[times.length - 1] : times[times.length - 1] / 100;

                    double[] kps = rList.at("KP").asDoubles();
                    Float kp = (float) kps[kps.length - 1];

                    LocalDateTime datetime = LocalDateTime.of(year, month, day, time, 0);

                    int nowHour =  (int) Math.floor((double)now.getHour() / 3) * 3;

                    now = LocalDateTime.now();
                    now = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), nowHour - 9, 0);

                    boolean append = !now.isEqual(datetime);

                    log.info("append : " + append);

                    // 데이터 생성
                    List<Forecast> forecasts = new ArrayList<>();
                    forecasts.add(Forecast.builder().kp(kp).dateTime(datetime).build());

                    for (int i = 0; i < 72; i++) {
                        datetime = datetime.plusHours(1);
                        Forecast forecast = Forecast.builder().kp((float) predictKp[i]).dateTime(datetime).build();
                        forecasts.add(forecast);
                    }

                    // 만약 데이터가 아직 업데이트가 안되었다면, 더미 데이터를 뒤에 3개 추가해주기
                    if (append) {
                        for (int i = 0; i < 3; i ++) {
                            datetime = datetime.plusHours(1);
                            Forecast forecast = Forecast.builder().kp((float) predictKp[predictKp.length -1]).dateTime(datetime).build();
                            forecasts.add(forecast);
                        }
                    }

                    // 테이블 비우기 및 데이터 삽입
                    List<Forecast> forecastAll = forecastRepository.findAll();
                    redisDAO.setString("forecast", forecastAll.toString(), 1L, TimeUnit.HOURS);
                    forecastRepository.deleteAllInBatch();
                    forecastRepository.saveAll(forecasts);
                    redisDAO.deleteString("forecast");

                }  catch (Exception e) {
                    e.printStackTrace();
                    log.info("R script를 실행하는 와중에 오류가 발생했습니다. ");
                }

                return RepeatStatus.FINISHED;
            }
        };
    }
}
