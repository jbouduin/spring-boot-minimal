package de.der_e_coach.shared_lib.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import de.der_e_coach.shared_lib.entity.SystemRole;


@Configuration
public class SecurityFilterChainConfiguration {
  //#region Private injected fields -------------------------------------------
  @Value("${management.endpoints.web.base-path}")
  private String actuatorBasePath;
  @Value("${der-e-coach.security.enable-cors: true}")
  private boolean enableCors;
  @Value("${der-e-coach.security.disable-crsf: true}")
  private boolean disableCsrf;
  @Value("${der-e-coach.security.permit-all-patterns:}")
  private String[] permitAllPatterns;
  @Value("${der-e-coach.security.admin-patterns:}")
  private String[] adminPatterns;
  //#endregion

  //#region private fields ---------------------------------------------------- 
  private final CorsConfigurationSource corsConfigurationSource;
  private final JwtValidationFilter jwtValidationFilter;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public SecurityFilterChainConfiguration(
    final CorsConfigurationSource corsConfigurationSource,
    final JwtValidationFilter jwtValidationFilter
  ) {
    this.corsConfigurationSource = corsConfigurationSource;
    this.jwtValidationFilter = jwtValidationFilter;
  }
  //#endregion

  //#region Configuration beans -----------------------------------------------
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    if (enableCors) {
      httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource));
    }
    if (disableCsrf) {
      httpSecurity.csrf(AbstractHttpConfigurer::disable);
    }

    String[] adminRoles = SystemRole.systemAdministratorValues();

    httpSecurity
      .authorizeHttpRequests(
        auth -> {
          // permit all endpoints if defined in application.configuration
          if (permitAllPatterns.length > 0) {
            auth
              .requestMatchers(permitAllPatterns)
              .permitAll();
          }
          // secure actuator
          auth
            // actuator health is accessible for all
            .requestMatchers(actuatorBasePath + "/health")
            .permitAll()
            // other actuator endpoints are only available for sys admins
            .requestMatchers(actuatorBasePath + "/**")
            .hasAnyRole(adminRoles);
          
          // secure administrator only endpoints if defined
          if (adminPatterns.length > 0) {
            auth
              .requestMatchers(adminPatterns)
              .hasAnyRole(adminRoles);
          }          
          // anything else must be authenticated
          auth
            .anyRequest()
            .authenticated();
        }
      )
      .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }
}
