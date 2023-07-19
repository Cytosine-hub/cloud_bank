package com.i2f.train.user;

import  org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableFeignClients
public class UserApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "user");
        SpringApplication.run(UserApplication.class, args);
    }
}
