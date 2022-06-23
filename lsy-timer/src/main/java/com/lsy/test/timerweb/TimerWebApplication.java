package com.lsy.test.timerweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimerWebApplication.class, args);
    }

}
