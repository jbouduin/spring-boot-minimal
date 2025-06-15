package de.der_e_coach.authentication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import de.der_e_coach.shared_lib.configuration.JwtValidationFilterImpl;

@SpringBootApplication()
@ComponentScan(
    basePackages = "de.der_e_coach", excludeFilters = {
      @ComponentScan.Filter(type = FilterType.REGEX, pattern = "de.der_e_coach.shared_lib.service.feign.*"),
      @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtValidationFilterImpl.class)
    }
)
public class AuthenticationServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(AuthenticationServiceApplication.class, args);
  }
}
