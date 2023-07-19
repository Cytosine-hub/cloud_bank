package com.i2f.train.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Cytosine
 */
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        System.setProperty("application.name", "gateway");
        SpringApplication.run(GatewayApplication.class, args);
    }
    /*
     * spring配置加载优先级
     * 1.系统环境变量
     * 2.java启动命令行变量
     * 3.system.setProperty()
     * 4.bootstrap
     * 5.application
     * */

}
