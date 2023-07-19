package com.i2f.train.codeProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Cytosine
 */
@SpringBootApplication
public class CodeProviderApplication {
    public static void main(String[] args) {
        System.setProperty("application.name", "code");
        SpringApplication.run(CodeProviderApplication.class, args);
    }

}
