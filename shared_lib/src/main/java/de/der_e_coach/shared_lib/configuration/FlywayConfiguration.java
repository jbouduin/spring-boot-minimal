package de.der_e_coach.shared_lib.configuration;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.JavaMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class FlywayConfiguration {
  //#region Private injected fields -------------------------------------------
  @Value("${der-e-coach.flyway.datasource.url}")
  private String flywayUrl;

  @Value("${der-e-coach.flyway.datasource.username}")
  private String flywayUsername;

  @Value("${der-e-coach.flyway.datasource.password}")
  private String flywayPassword;

  @Value("${der-e-coach.flyway.datasource.driver-class-name}")
  private String flywayDriverClassName;

  @Value("${der-e-coach.flyway.datasource.default_schema}")
  private String flywaySchemas;

  @Value("${der-e-coach.flyway.validate-on-migrate}")
  private boolean flywayValidateOnMigrate;

  @Value("${der-e-coach.flyway.baseline-on-migrate}")
  private boolean flywayBaseLineOnMigrate;

  @Value("${der-e-coach.flyway.baseline-version}")
  private String flywayBaseLineVersion;

  @Value("${der-e-coach.flyway.enabled}")
  private boolean flywayEnabled;
  //#endregion

  //#region Private fields ----------------------------------------------------
  private final ApplicationContext applicationContext;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public FlywayConfiguration(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
  //#endregion

  //#region Configuration beans -----------------------------------------------
  @Bean
  public Flyway flyway() {
    /**
     * Do not add "classpath:de/der_e_coach/site_service/db/migration" to the Flyway
     * locations.
     */
    if (flywayEnabled) {

      DataSource flywayDataSource = new DriverManagerDataSource(flywayUrl, flywayUsername, flywayPassword);
      Flyway flyway = Flyway
          .configure()
          .dataSource(flywayDataSource)
          .schemas(flywaySchemas)
          .validateOnMigrate(flywayValidateOnMigrate)
          .baselineOnMigrate(flywayBaseLineOnMigrate)
          .baselineVersion(flywayBaseLineVersion)
          .javaMigrations(applicationContext.getBeansOfType(JavaMigration.class).values().toArray(new JavaMigration[0]))
          .load();
      return flyway;
    } else {
      return null;
    }
  }

  @Bean
  /**
   * Use a delayed strategy, so repositories are created before starting the
   * migration
   *
   * @param flyway
   * @return
   */
  public FlywayMigrationStrategy flywayMigrationStrategy(Flyway flyway) {
    return new DelayedFlywayMigrationStrategy(flyway);
  }
  // #endregion
}
