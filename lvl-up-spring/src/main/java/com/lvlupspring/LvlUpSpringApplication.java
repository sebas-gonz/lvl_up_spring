package com.lvlupspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LvlUpSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LvlUpSpringApplication.class, args);
    }

}
