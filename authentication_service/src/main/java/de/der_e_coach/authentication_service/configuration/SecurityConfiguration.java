package de.der_e_coach.authentication_service.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class SecurityConfiguration {
  //#region Constructor -------------------------------------------------------
  @Autowired
  public SecurityConfiguration() {
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
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  //#endregion
}
