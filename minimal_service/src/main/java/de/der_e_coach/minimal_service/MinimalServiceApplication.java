package de.der_e_coach.minimal_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "de.der_e_coach")
@EnableFeignClients
public class MinimalServiceApplication {  
  public static void main(String[] args) {
    SpringApplication.run(MinimalServiceApplication.class, args);
  }
}
