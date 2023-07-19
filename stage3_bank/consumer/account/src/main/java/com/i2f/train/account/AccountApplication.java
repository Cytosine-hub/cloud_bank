package com.i2f.train.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AccountApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "account");
        SpringApplication.run(AccountApplication.class, args);
    }

}
