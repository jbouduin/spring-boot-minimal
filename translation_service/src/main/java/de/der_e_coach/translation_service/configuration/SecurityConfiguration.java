package de.der_e_coach.translation_service.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
  //#region private fields ---------------------------------------------------- 
  private final JwtValidationFilter jwtValidationFilter;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public SecurityConfiguration(final JwtValidationFilter jwtValidationFilter) {
    this.jwtValidationFilter = jwtValidationFilter;
  }
  //#endregion

  //#region Configuration beans -----------------------------------------------
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(
        auth -> auth
          // swagger page is available for all
          .requestMatchers("/docs/**")
          .permitAll()
          // system/info and error is available for all
          .requestMatchers("/system/info", "/error")
          .permitAll()
          .anyRequest()
          .authenticated()
      )
      .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern("*");
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  //#endregion  
}
