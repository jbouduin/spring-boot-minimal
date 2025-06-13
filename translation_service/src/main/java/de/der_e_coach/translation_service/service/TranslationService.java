package de.der_e_coach.translation_service.service;

import java.util.List;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.translation_service.dto.TranslationDto;
import de.der_e_coach.translation_service.entity.Translation;

public interface TranslationService {
  /**
   * Create translations
   * 
   * @param  translations a list of {@link TranslationDto}
   * @return
   */
  ResultDto<List<TranslationDto>> createTranslations(List<TranslationDto> translations);
  /**
   * Find translation using optional filters
   * 
   * @param  entityNames  optional list of entity names
   * @param  entityFields optional list of entity fields
   * @param  entityIds    optional list of entity id's
   * @param  languages    optional list of languages
   * @return              list of found {@link Translation}
   */
  ResultDto<List<TranslationDto>> getTranslations(
    List<String> entityNames,
    List<String> entityFields,
    List<Long> entityIds,
    List<LanguageCode> languages
  );
  /**
   * Create, Update and Merge translations in one go.
   * 
   * @param  mergeTranslations list of {@link TranslationMergeDto}
   * @return                   a {@link ResultDto} containing created and updated {@link TranslationDto}
   */
  ResultDto<List<TranslationDto>> mergeTranslations(List<TranslationMergeDto> translationMergeDtos);
}
