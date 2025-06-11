package de.der_e_coach.shared_lib.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DelayedFlywayMigrationStrategy
  implements FlywayMigrationStrategy, ApplicationListener<ContextRefreshedEvent> {
  //#region Private fields ----------------------------------------------------
  private final Flyway flyway;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public DelayedFlywayMigrationStrategy(Flyway flyway) {
    this.flyway = flyway;
  }
  //#endregion

  //#region FlywayMigrationStrategy members -----------------------------------
  @Override
  public void migrate(Flyway flyway) {
    // Do nothing here, migration will be triggered on context refresh
  }
  //#endregion

  //#region ApplicationListener members ---------------------------------------
  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    flyway.migrate();
  }
  //#endregion
}
