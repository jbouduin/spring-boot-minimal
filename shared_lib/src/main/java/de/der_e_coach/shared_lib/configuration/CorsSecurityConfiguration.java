package de.der_e_coach.shared_lib.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsSecurityConfiguration {
  @Value("${der-e-coach.cors.allowed_origin}")
  private String allowedOrigin;
  @Value("${der-e-coach.cors.allowed_methods}")
  private String[] allowedMethods;
  @Value("${der-e-coach.cors.allowed_headers}")
  private String[] allowedHeaders;
  @Value("${der-e-coach.cors.allow_credentials}")
  private boolean allowCredentials;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern(allowedOrigin);
    configuration.setAllowedMethods(Arrays.asList(allowedMethods));
    configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
    configuration.setAllowCredentials(allowCredentials);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
