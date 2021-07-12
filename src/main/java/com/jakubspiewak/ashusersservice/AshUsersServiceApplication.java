package com.jakubspiewak.ashusersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaClient
@SpringBootApplication
public class AshUsersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AshUsersServiceApplication.class, args);
    }

}
