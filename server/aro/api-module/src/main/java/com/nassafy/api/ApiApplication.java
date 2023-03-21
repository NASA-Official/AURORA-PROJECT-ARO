package com.nassafy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@ComponentScan({"com.nassafy.core"})
@ComponentScan({"com.nassafy.api"})
@EntityScan("com.nassafy.core")
@EnableJpaRepositories("com.nassafy.core")
public class ApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class, args);
        // 다른 모듈의 yml 읽어오기 위해서 사용
        System.setProperty("spring.config.name", "application, application-core");
    }

}
