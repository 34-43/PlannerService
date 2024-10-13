package com.sparta.plannerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PlannerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerServiceApplication.class, args);
    }

}
