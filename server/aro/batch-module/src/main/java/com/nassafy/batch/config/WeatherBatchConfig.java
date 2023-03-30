package com.nassafy.batch.config;


import com.nassafy.batch.service.OpenWeatherMapService;
import com.nassafy.core.entity.Weather;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class WeatherBatchConfig {
    @Autowired
    private WeatherRepository weatherRepository;
    private final float[][] COORDINATESlIST = {
            {64.133f, -21.933f},
            {64.327751f, -20.121331f},
            {65.68389f, -18.11056f},
            {64.068833058f, -16.206999172f},
            {63.418632f, -19.006048f},
            {64.043065f, -16.175841f},
            {62.453972f, -114.371788f},
            {63.000000f, -136.42595f},
            {55.37662690166161f, -105.18045166303939f},
            {58.76469960491638f, -94.15337484537265f},
            {45.761183411435816f, -81.99587764549844f},
            {69.68694626945293f, 18.911182391491675f},
            {78.22347549779091f, 15.631056044369549f},
            {70.42546615383817f, 22.92355263279395f},
            {69.96846130103587f, 23.271064438030503f},
            {68.43736386967925f, 17.424795068871937f},
            {67.93264970202742f, 13.088974631925087f},
            {66.61914686077237f, 25.691168791077867f},
            {65.7381979212172f, 24.56354429098606f},
            {68.65652890984954f, 27.539441924225045f},
            {65.08078787047003f, 25.541695944452595f},
            {60.1823305612937f, 24.925277642753198f},
            {68.42091116264578f, 27.411647633009256f},
            {59.33297196875003f, 18.065823248667762f},
            {67.8565705445105f, 20.222471031168844f},
            {68.34971145357709f, 18.830945406277774f},
            {67.85079631137044f, 20.602901281655672f},
            {63.18610158048239f, -151.193125664042f},
            {47.71308977479624f, -116.67694765564325f},
            {46.974720130335896f, -67.8729970082217f},
            {41.898786620545046f, -87.74496754139466f},
            {46.56258654638332f, -87.40088613002773f},
            {64.84027002967417f, -147.71020560334486f},
            {69.05009625141787f, 33.034014578171266f},
            {67.61545135646637f, 33.66311657578932f},
            {69.34751936597033f, -51.04003758638136f},
            {64.21984213741572f, -51.75804883730757f},
            {66.93951482128469f, -53.67191358610414f}
    };

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @Bean
    public Job weatherJob() {
        return jobBuilderFactory.get("weatherJob")
                .start(weatherStep())
                .build();
    }

    @Bean
    @JobScope
    public Step weatherStep() {
        return stepBuilderFactory.get("weatherStep")
                .<float[], List<Weather>>chunk(1)
                .reader(coordinatesReader())
                .processor(weatherDataProcessor())
                .writer(weatherDataWriter())
                .build();
    }

    @Bean
    public ListItemReader<float[]> coordinatesReader() {
        return new ListItemReader<>(Arrays.asList(COORDINATESlIST));
    }

    @Bean
    public ItemProcessor<float[], List<Weather>> weatherDataProcessor() {
        return coordinates -> openWeatherMapService.fetchWeatherData(coordinates[0], coordinates[1]);
    }

    // 데이터 삭제 및 저장
    @Bean
    @Transactional()
    public ItemWriter<List<Weather>> weatherDataWriter(){
        weatherRepository.deleteAllInBatch();
        return items -> {
            for (List<Weather> weatherData : items) {
                for (Weather weather : weatherData) {
                    weatherRepository.save(weather);
                }
            }
        };
    }

}
