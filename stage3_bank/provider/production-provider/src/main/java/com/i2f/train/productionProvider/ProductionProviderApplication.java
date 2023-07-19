package com.i2f.train.productionProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductionProviderApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "production-provider");
        SpringApplication.run(ProductionProviderApplication.class, args);
    }

}
