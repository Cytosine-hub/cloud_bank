package com.i2f.train.financeProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class FinanceProviderApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "finance-provider");
        SpringApplication.run(FinanceProviderApplication.class, args);
    }

}
