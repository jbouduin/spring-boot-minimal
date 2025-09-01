package de.der_e_coach.translation_service.db.migration;

import java.util.List;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.entity.SystemRole;
import de.der_e_coach.translation_service.dto.TranslationDto;
import de.der_e_coach.translation_service.service.TranslationService;

/**
 * Bean-based migration class to create the translations for the supported
 * languages.
 * The dependencies are not directly injected as it has not been created when
 * flyway is configured and that would leave
 * this bean out of the list of migration beans.
 */
@Component
public class V01_00_004__Role_Translations extends BaseJavaMigration {
  //#region Private fields ----------------------------------------------------
  private final ApplicationContext applicationContext;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public V01_00_004__Role_Translations(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
  //#endregion

  //#region BaseJavaMigration members -----------------------------------------
  @Override
  public void migrate(Context context) throws Exception {
    TranslationService translationService = applicationContext.getBean(TranslationService.class);

    translationService
      .createTranslations(
        List
          .of(
            // System administrator
            new TranslationDto(SystemRole.SYS_ADMIN.getTranslationKey(), LanguageCode.DE, "Systemadministrator"),
            new TranslationDto(SystemRole.SYS_ADMIN.getTranslationKey(), LanguageCode.EN, "System Administrator"),
            new TranslationDto(SystemRole.SYS_ADMIN.getTranslationKey(), LanguageCode.NL, "Systembeheerder"),
            new TranslationDto(SystemRole.SYS_ADMIN.getTranslationKey(), LanguageCode.FR, "Administrateur système"),
            // User
            new TranslationDto(SystemRole.USER.getTranslationKey(), LanguageCode.DE, "Benutzer (ohne erhöhte Rechte)"),
            new TranslationDto(SystemRole.USER.getTranslationKey(), LanguageCode.EN, "User (without elevated rights)"),
            new TranslationDto(SystemRole.USER.getTranslationKey(), LanguageCode.NL, "Gebruiker (zonder verhoogde rechten)"),
            new TranslationDto(SystemRole.USER.getTranslationKey(), LanguageCode.FR, "Utilisateur (sans droits élevés)")
          )
      );
  }
  //#endregion
}
