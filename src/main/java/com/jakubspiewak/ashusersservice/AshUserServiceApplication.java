package com.jakubspiewak.ashusersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AshUserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AshUserServiceApplication.class, args);
  }
}
