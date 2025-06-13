package de.der_e_coach.translation_service.dto;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;

import de.der_e_coach.shared_lib.dto.translation.TranslationKeyDto;
import de.der_e_coach.translation_service.entity.Translation;

public class TranslationDto extends de.der_e_coach.shared_lib.dto.translation.TranslationDto {
  //#region Constructor -------------------------------------------------------
  public TranslationDto(Translation translation) {
    super(
      new TranslationKeyDto(translation.getEntityName(), translation.getEntityField()),
      translation.getEntityId(),
      translation.getLanguage(),
      translation.getText()
    );
  }

  public TranslationDto(TranslationKeyDto key, LanguageCode language, String text) {
    super(key, Translation.NONE_ENTITY_ID, language, text);
  }
  
  public TranslationDto() {
  }
  //#endregion
}
