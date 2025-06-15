package de.der_e_coach.authentication_service.configuration;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
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
  public UserDetailsManager userDetailsManager(DataSource dataSource) {
    JdbcUserDetailsManager result = new JdbcUserDetailsManager(dataSource);
    result.setUsersByUsernameQuery("""
      SELECT
        a.account_name,
        a.password,
        a.active
      FROM authentication_service.account a
      WHERE
        LOWER(?) IN (LOWER(a.account_name), LOWER(a.email))
      """);
    /*
     * the table is called account_role and not just role,
     * as some SQL editors identify it as a keyword
     */
    result.setAuthoritiesByUsernameQuery("""
      SELECT
        a.account_name,
        r.account_role
      FROM authentication_service.account a
      JOIN authentication_service.account_role r
        ON r.account_id  = a.id
      WHERE
        LOWER(?) IN (LOWER(a.account_name), LOWER(a.email))
      """);

    result
      .setCreateUserSql(
        "INSERT INTO authentication_service.account (account_name, password, active) VALUES (?, ?, ?)"
      );
    result.setCreateAuthoritySql("""
      INSERT
        INTO authentication_service.account_role (account_id, account_role)
        VALUES
          ((SELECT a.id FROM authentication_service.account a WHERE a.account_name = ?), ?)
      """);
    // user exists is for own use and does not look to the email
    result.setUserExistsSql("SELECT a.account_name FROM authentication_service.account a WHERE a.account_name= ?");
    return result;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      // as there are no forms submitting data, we can disable CSRF
      .csrf(AbstractHttpConfigurer::disable)
      .cors(cors -> cors.configurationSource(corsConfigurationSource()))
      .authorizeHttpRequests(
        auth -> auth
          // swagger page is available for all
          .requestMatchers("docs/**")
          .permitAll()
          // actuator endpoints are only available for sys admins
          .requestMatchers(EndpointRequest.toAnyEndpoint())
          .hasRole("SYS_ADMIN")
          // login is available for all
          .requestMatchers("login", "authorize")
          .permitAll()
          // anything else must be SYS_ADMIN
          .anyRequest()
          .hasRole("SYS_ADMIN")
      )
      .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
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
