package de.der_e_coach.minimal_service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
      .cors(Customizer.withDefaults())
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
  //#endregion  
}
