package com.i2f.train.accountProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

/**
 * @author wqshandwzh
 */
@SpringBootApplication
public class AccountProviderApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "account-provider");
        SpringApplication.run(AccountProviderApplication.class, args);
    }

}
