package com.nassafy.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableBatchProcessing
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@ComponentScan({"com.nassafy.core"})
@ComponentScan({"com.nassafy.batch"})
@EntityScan("com.snasafy.core")
@EnableJpaRepositories("com.nassafy.core")
public class BatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

}
