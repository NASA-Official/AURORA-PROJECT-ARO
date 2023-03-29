package com.nassafy.batch.config;


import com.nassafy.batch.service.OpenWeatherMapService;
import com.nassafy.core.entity.Weather;
import com.nassafy.core.respository.WeatherRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class WeatherBatchConfig {
    public static final List<float[]> COORDINATES_LIST = List.of(
            new float[]{64.133f, -21.933f},
            new float[]{64.327751f, -20.121331f},
            new float[]{65.68389f, -18.11056f},
            new float[]{64.068833058f, -16.206999172f},
            new float[]{63.418632f, -19.006048f},
            new float[]{64.043065f, -16.175841f},
            new float[]{62.453972f, -114.371788f},
            new float[]{63.000000f, -136.42595f},
            new float[]{55.37662690166161f, -105.18045166303939f},
            new float[]{58.76469960491638f, -94.15337484537265f},
            new float[]{45.761183411435816f, -81.99587764549844f},
            new float[]{69.68694626945293f, 18.911182391491675f},
            new float[]{78.22347549779091f, 15.631056044369549f},
            new float[]{70.42546615383817f, 22.92355263279395f},
            new float[]{69.96846130103587f, 23.271064438030503f},
            new float[]{68.43736386967925f, 17.424795068871937f},
            new float[]{67.93264970202742f, 13.088974631925087f},
            new float[]{66.61914686077237f, 25.691168791077867f},
            new float[]{65.7381979212172f, 24.56354429098606f},
            new float[]{68.65652890984954f, 27.539441924225045f},
            new float[]{65.08078787047003f, 25.541695944452595f},
            new float[]{60.1823305612937f, 24.925277642753198f},
            new float[]{68.42091116264578f, 27.411647633009256f},
            new float[]{59.33297196875003f, 18.065823248667762f},
            new float[]{67.8565705445105f, 20.222471031168844f},
            new float[]{68.34971145357709f, 18.830945406277774f},
            new float[]{67.85079631137044f, 20.602901281655672f},
            new float[]{63.18610158048239f, -151.193125664042f},
            new float[]{47.71308977479624f, -116.67694765564325f},
            new float[]{46.974720130335896f, -67.8729970082217f},
            new float[]{41.898786620545046f, -87.74496754139466f},
            new float[]{46.56258654638332f, -87.40088613002773f},
            new float[]{64.84027002967417f, -147.71020560334486f},
            new float[]{69.05009625141787f, 33.034014578171266f},
            new float[]{67.61545135646637f, 33.66311657578932f},
            new float[]{69.34751936597033f, -51.04003758638136f},
            new float[]{64.21984213741572f, -51.75804883730757f},
            new float[]{66.93951482128469f, -53.67191358610414f}
    );

    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @Bean
    public Job weatherJob(Step weatherStep) {
        return jobBuilderFactory.get("weatherJob")
                .start(weatherStep)
                .build();
    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Step weatherStep(ItemReader<float[]> coordinatesReader,
                            ItemProcessor<float[], List<Weather>> weatherDataProcessor,
                            ItemWriter<List<Weather>> weatherDataWriter) {
        return stepBuilderFactory.get("weatherStep")
                .<float[], List<Weather>>chunk(38)
                .reader(coordinatesReader)
                .processor(weatherDataProcessor)
                .writer(weatherDataWriter)
                .build();
    }

    @Bean
    public ListItemReader<float[]> coordinatesReader() {
        return new ListItemReader<>(COORDINATES_LIST);
    }

    @Bean
    public ItemProcessor<float[], List<Weather>> weatherDataProcessor() {
        return coordinates -> openWeatherMapService.fetchWeatherData(coordinates[0], coordinates[1]);
    }

    @Bean
    public ItemWriter<List<Weather>> weatherDataWriter() {
        return items -> {
            weatherRepository.deleteAll();
            items.forEach(weatherData -> weatherData.forEach(weatherRepository::save));
        };
    }
}
