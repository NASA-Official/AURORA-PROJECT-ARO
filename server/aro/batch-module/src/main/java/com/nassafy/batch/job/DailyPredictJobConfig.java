package com.nassafy.batch.job;

import com.nassafy.core.entity.Forecast;
import com.nassafy.core.respository.ForecastRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DailyPredictJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ForecastRepository forecastRepository;

    @Bean
    public Job dailyPredictJob() {
        log.info("********** This is dailyPredictJob");

        return jobBuilderFactory.get("dailyPredictJob")
                .preventRestart()
                .start(dailyPredictStep())
                .build();
    }

    public Step dailyPredictStep() {
        log.info("********** This is dailyPredictStep");
        return stepBuilderFactory.get("dailyPredictStep")
                .tasklet(dailyPredictTasklet(null))
                .build();
    }


    /*
     * 해당 tasklet은 72시간 데이터를
     * 0. R에서 분석 데이터 가지고 오기
     * 1. 날짜가 최신 날짜가 맞는지 확인
     * 2. 날짜가 최신 날짜가 아니라면 종료, 최신 날짜라면 업데이트하기
     */
    @Bean
    @StepScope
    public Tasklet dailyPredictTasklet(@Value("#{jobParameters[time]}") String time) {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                RConnection conn = null;
                try {
                    conn = new RConnection("j8d106.p.ssafy.io", 6311);  // // 로컬에서는 host를 j8d106.p.ssafy.io로, 코드 올릴 때는 rstudio로 변경하기

                    // input date 계산하기(1달 전)
                    LocalDateTime now = LocalDateTime.now();
                    now = now.minusMonths(1);
                    String input_date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();

                    // R script 실행하기
                    conn.eval("source('/home/rstudio/dailyPredict.R')");
                    conn.eval("regulate <- crawlingData('" + input_date + "')");
                    RList rList = conn.eval("regulate").asList();
                    double[] predictKp = conn.eval("predictDailyKP(regulate, 24)").asDoubles();

                    log.info("R script 실행 완료");

                    // 마지막 시간 받아오기
                    String[] years = rList.at("YEAR").asStrings();
                    int[] months = rList.at("MONTH").asIntegers();
                    int[] days = rList.at("DAY").asIntegers();
                    int[] times = rList.at("TIME").asIntegers();

                    int year = Integer.parseInt(years[years.length - 1]);
                    int month = months[months.length - 1];
                    int day = days[days.length - 1];
                    int time = times[times.length - 1] == 0 ? times[times.length - 1] : times[times.length - 1] / 100;

                    LocalDateTime datetime = LocalDateTime.of(year, month, day, time, 0);  // 마지막 예측 시간

                    now = LocalDateTime.now().minusHours(9); // 현재 한국 시간을 utc시간이랑 맞춰주기
                    now = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), 0); // 분 정보는 삭제하기


                    // 만약 저장할 데이터가 없다면 작업을 중지합니다.
                    if (datetime.plusHours(24).isBefore(now)) {
                        log.info("update할 data가 없습니다.");
                        return RepeatStatus.FINISHED;
                    }

                    // 저장할 데이터가 있는 경우에 저장합니다.
                    int cnt = 0;
                    for (int i = 0; i < 24; i++) {
                        datetime = datetime.plusHours(1);
                        if (datetime.isAfter(now)) {
                            try {
                                Forecast forecast = forecastRepository.findByDateTime(datetime)
                                        .orElseThrow(IllegalArgumentException::new);
                                forecast.updateKp((float) predictKp[i]);
                                forecastRepository.save(forecast);
                                cnt += 1;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    log.info(cnt + "개의 데이터가 업데이트 되었습니다.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return RepeatStatus.FINISHED;
            }
        };
    }
}
