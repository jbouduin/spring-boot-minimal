package de.der_e_coach.translation_service.dto;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.dto.translation.TranslatableEnum;
import de.der_e_coach.translation_service.entity.Translation;

public class TranslationDto extends de.der_e_coach.shared_lib.dto.translation.TranslationDto {
  //#region Constructor -------------------------------------------------------
  public TranslationDto(Translation translation) {
    super(
      translation.getEntityName(),
      translation.getEntityField(),
      translation.getEntityId(),
      translation.getLanguageCode(),
      translation.getText()
    );
  }

  public TranslationDto(TranslatableEnum key, LanguageCode languageCode, String text) {
    super(
      key,
      languageCode,
      text
    );
  }

  public TranslationDto() {
  }
  // #endregion
}
