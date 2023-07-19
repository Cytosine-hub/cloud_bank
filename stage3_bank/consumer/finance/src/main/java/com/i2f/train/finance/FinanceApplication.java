package com.i2f.train.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinanceApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "finance");
        SpringApplication.run(FinanceApplication.class, args);
    }

}
