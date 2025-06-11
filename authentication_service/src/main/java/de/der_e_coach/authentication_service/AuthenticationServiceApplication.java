package de.der_e_coach.authentication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.der_e_coach")
public class AuthenticationServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthenticationServiceApplication.class, args);
  }
}
