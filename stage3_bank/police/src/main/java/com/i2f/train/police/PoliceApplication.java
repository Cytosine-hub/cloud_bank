package com.i2f.train.police;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PoliceApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "police");
        SpringApplication.run(PoliceApplication.class, args);
    }

}
