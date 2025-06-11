package de.der_e_coach.authentication_service.db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * Bean-based migration class to create the admin user with user name and password as specified in the configuration
 * properties. The user is assigned the roles "SYS_ADMIN" and "SITE_ADMIN".
 */
@Component
public class V01_00_003__Security_Tables_Data extends BaseJavaMigration {
  //#region Private injected fields -------------------------------------------
  @Value("${authentication-service.first-usage-admin-user:admin}")
  private String firstUseAdminUser;
  @Value("${authentication-service.first-usage-admin-password:admin}")
  private String firstUseAdminPassword;
  //#endregion

  //#region Private fields ----------------------------------------------------
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsManager jdbcUserDetailsManager;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public V01_00_003__Security_Tables_Data(PasswordEncoder passwordEncoder, UserDetailsManager jdbcUserDetailsManager) {
    this.passwordEncoder = passwordEncoder;
    this.jdbcUserDetailsManager = jdbcUserDetailsManager;
  }
  //#endregion

  //#region BaseJavaMigration members -----------------------------------------
  @Override
  public void migrate(Context context) throws Exception {
    UserBuilder builder = User
      .builder()
      .username(firstUseAdminUser)
      .password(
        passwordEncoder.encode(firstUseAdminPassword)
      )
      .roles("SYS_ADMIN", "SITE_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
    // following users should not be created in a prod environment
    builder = User
      .builder()
      .username("system")
      .password(
        passwordEncoder.encode("system")
      )
      .roles("SYS_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
    builder = User
      .builder()
      .username("site")
      .password(
        passwordEncoder.encode("site")
      )
      .roles("SITE_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
  }
  // #endregion
}
