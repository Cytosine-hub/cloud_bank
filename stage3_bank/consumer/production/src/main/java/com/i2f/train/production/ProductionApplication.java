package com.i2f.train.production;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductionApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "production");
        SpringApplication.run(ProductionApplication.class, args);
    }

}