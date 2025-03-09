package com.kulachok.kulachok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.kulachok.kulachok", "util"})
public class KulachokApplication {

    public static void main(String[] args) {
        SpringApplication.run(KulachokApplication.class, args);
    }
}
