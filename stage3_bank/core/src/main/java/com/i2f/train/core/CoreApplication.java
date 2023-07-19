package com.i2f.train.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CoreApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "core");
        SpringApplication.run(CoreApplication.class, args);
    }

}
