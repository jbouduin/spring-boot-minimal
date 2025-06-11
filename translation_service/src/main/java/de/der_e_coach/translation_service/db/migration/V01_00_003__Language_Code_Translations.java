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
  // #region Private fields ---------------------------------------------------
  private final ApplicationContext applicationContext;
  // #endregion

  // #region Constructor ------------------------------------------------------
  @Autowired
  public V01_00_003__Language_Code_Translations(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
  // #endregion

  // #region BaseJavaMigration members ----------------------------------------
  @Override
  public void migrate(Context context) throws Exception {
    TranslationService translationService = applicationContext.getBean(TranslationService.class);

    translationService
      .createTranslations(
        List
          .of(
            // German
            new TranslationDto(LanguageCode.DE, LanguageCode.DE, "Deutsch"),
            new TranslationDto(LanguageCode.DE, LanguageCode.EN, "German"),
            new TranslationDto(LanguageCode.DE, LanguageCode.NL, "Duits"),
            new TranslationDto(LanguageCode.DE, LanguageCode.FR, "Allemand"),
            // English
            new TranslationDto(LanguageCode.EN, LanguageCode.DE, "Englisch"),
            new TranslationDto(LanguageCode.EN, LanguageCode.EN, "English"),
            new TranslationDto(LanguageCode.EN, LanguageCode.NL, "Engels"),
            new TranslationDto(LanguageCode.EN, LanguageCode.FR, "Anglais"),
            // Dutch
            new TranslationDto(LanguageCode.NL, LanguageCode.DE, "Niederländisch"),
            new TranslationDto(LanguageCode.NL, LanguageCode.EN, "Dutch"),
            new TranslationDto(LanguageCode.NL, LanguageCode.NL, "Nederlands"),
            new TranslationDto(LanguageCode.NL, LanguageCode.FR, "Néerlandais"),
            // French
            new TranslationDto(LanguageCode.FR, LanguageCode.DE, "Französisch"),
            new TranslationDto(LanguageCode.FR, LanguageCode.EN, "French"),
            new TranslationDto(LanguageCode.FR, LanguageCode.NL, "Frans"),
            new TranslationDto(LanguageCode.FR, LanguageCode.FR, "Français")
          )
      );
  }
  // #endregion
}
