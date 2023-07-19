package com.i2f.train.userProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserProviderApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "user-provider");
        SpringApplication.run(UserProviderApplication.class, args);
    }

}
