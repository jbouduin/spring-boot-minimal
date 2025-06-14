package de.der_e_coach.translation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "de.der_e_coach")
@EnableFeignClients(basePackages = "de.der_e_coach.shared_lib.service.feign.authentication_service")
public class TranslationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslationServiceApplication.class, args);
	}

}
