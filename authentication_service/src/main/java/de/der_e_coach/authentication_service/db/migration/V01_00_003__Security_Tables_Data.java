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
 * Bean-based migration class to create the admin and system users with user names and passwords as specified in the
 * application properties. The users are assigned the roles "SYS_ADMIN" and "SITE_ADMIN".
 */
@Component
public class V01_00_003__Security_Tables_Data extends BaseJavaMigration {
  //#region Private injected fields -------------------------------------------
  @Value("${der-e-coach.authentication.first-usage-admin-user:admin}")
  private String firstUseAdminUser;
  @Value("${der-e-coach.authentication.first-usage-admin-password:admin}")
  private String firstUseAdminPassword;
  @Value("${der-e-coach.authentication.system-user:system}")
  private String systemUser;
  @Value("${der-e-coach.authentication.system-password:system}")
  private String systemPassword;
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
      .username(systemUser)
      .password(
        passwordEncoder.encode(systemPassword)
      )
      .roles("SYS_ADMIN", "SITE_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
    builder = User
      .builder()
      .username(firstUseAdminUser)
      .password(
        passwordEncoder.encode(firstUseAdminPassword)
      )
      .roles("SYS_ADMIN", "SITE_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
    // following users should not be created in a prod environment ;)
    builder = User
      .builder()
      .username("sys_admin")
      .password(
        passwordEncoder.encode("sys_admin")
      )
      .roles("SYS_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
    builder = User
      .builder()
      .username("site_admin")
      .password(
        passwordEncoder.encode("site_admin")
      )
      .roles("SITE_ADMIN");
    jdbcUserDetailsManager.createUser(builder.build());
  }
  // #endregion
}
