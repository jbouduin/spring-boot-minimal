package de.der_e_coach.translation_service.db.migration;

import java.util.List;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
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
public class V01_00_003__Language_Code_Translations extends BaseJavaMigration {
  //#region Private fields ----------------------------------------------------
  private final ApplicationContext applicationContext;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public V01_00_003__Language_Code_Translations(ApplicationContext applicationContext) {
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
            // German
            new TranslationDto(LanguageCode.DE.getTranslationKey(), LanguageCode.DE, "Deutsch"),
            new TranslationDto(LanguageCode.DE.getTranslationKey(), LanguageCode.EN, "German"),
            new TranslationDto(LanguageCode.DE.getTranslationKey(), LanguageCode.NL, "Duits"),
            new TranslationDto(LanguageCode.DE.getTranslationKey(), LanguageCode.FR, "Allemand"),
            // English
            new TranslationDto(LanguageCode.EN.getTranslationKey(), LanguageCode.DE, "Englisch"),
            new TranslationDto(LanguageCode.EN.getTranslationKey(), LanguageCode.EN, "English"),
            new TranslationDto(LanguageCode.EN.getTranslationKey(), LanguageCode.NL, "Engels"),
            new TranslationDto(LanguageCode.EN.getTranslationKey(), LanguageCode.FR, "Anglais"),
            // Dutch
            new TranslationDto(LanguageCode.NL.getTranslationKey(), LanguageCode.DE, "Niederländisch"),
            new TranslationDto(LanguageCode.NL.getTranslationKey(), LanguageCode.EN, "Dutch"),
            new TranslationDto(LanguageCode.NL.getTranslationKey(), LanguageCode.NL, "Nederlands"),
            new TranslationDto(LanguageCode.NL.getTranslationKey(), LanguageCode.FR, "Néerlandais"),
            // French
            new TranslationDto(LanguageCode.FR.getTranslationKey(), LanguageCode.DE, "Französisch"),
            new TranslationDto(LanguageCode.FR.getTranslationKey(), LanguageCode.EN, "French"),
            new TranslationDto(LanguageCode.FR.getTranslationKey(), LanguageCode.NL, "Frans"),
            new TranslationDto(LanguageCode.FR.getTranslationKey(), LanguageCode.FR, "Français")
          )
      );
  }
  //#endregion
}
