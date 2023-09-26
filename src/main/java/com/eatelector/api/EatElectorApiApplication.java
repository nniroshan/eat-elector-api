package com.eatelector.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EatElectorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatElectorApiApplication.class, args);
    }

}
