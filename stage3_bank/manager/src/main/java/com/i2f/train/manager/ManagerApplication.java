package com.i2f.train.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableFeignClients
public class ManagerApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "manager");
        SpringApplication.run(ManagerApplication.class, args);
    }

}
