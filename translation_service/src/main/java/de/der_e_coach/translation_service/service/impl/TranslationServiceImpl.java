package de.der_e_coach.translation_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.translation_service.dto.TranslationDto;
import de.der_e_coach.translation_service.entity.Translation;
import de.der_e_coach.translation_service.repository.TranslationRepository;
import de.der_e_coach.translation_service.service.TranslationService;
import jakarta.transaction.Transactional;

@Service
public class TranslationServiceImpl implements TranslationService {
  //#region Private fields ----------------------------------------------------
  private final TranslationRepository translationRepository;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public TranslationServiceImpl(TranslationRepository translationRepository) {
    this.translationRepository = translationRepository;
  }
  //#endregion

  //#region TranslationService Members ----------------------------------------
  @Override
  @Transactional
  public ResultDto<List<TranslationDto>> createTranslations(List<TranslationDto> translations) {
    List<TranslationDto> savedTranslations = translationRepository
      .saveAll(
        translations
          .stream()
          .map((TranslationDto t) -> new Translation(t))
          .collect(Collectors.toList())
      )
      .stream()
      .map((Translation t) -> new TranslationDto(t))
      .collect(Collectors.toList());
    return ResultDto.success(savedTranslations);
  }

  @Override
  public ResultDto<List<TranslationDto>> getTranslations(
    List<String> entityNames,
    List<String> entityFields,
    List<Long> entityIds,
    List<LanguageCode> languages
  ) {
    return ResultDto
      .success(
        translationRepository
          .findTranslations(entityNames, entityFields, entityIds, languages)
          .stream()
          .map((Translation t) -> new TranslationDto(t))
          .collect(Collectors.toList())
      );
  }
  //#endregion
}
