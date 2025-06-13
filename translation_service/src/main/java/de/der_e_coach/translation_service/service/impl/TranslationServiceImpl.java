package de.der_e_coach.translation_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.result.ValidationError;
import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationRecord;
import de.der_e_coach.translation_service.dto.TranslationDto;
import de.der_e_coach.translation_service.entity.Translation;
import de.der_e_coach.translation_service.entity.TranslationId;
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

  @Override
  @Transactional
  public ResultDto<List<TranslationDto>> mergeTranslations(List<TranslationMergeDto> translationMergeDtos) {
    ResultDto<List<TranslationDto>> result;
    if (translationMergeDtos.isEmpty()) {
      result = ResultDto
        .invalid(
          List
            .of(
              new ValidationError(
                this.getClass(), "mergeTranslations", "001", "List of TranslationMergeDto's may not be empty."
              )
            )
        );
    } else {
      // convert input to a list of translations to be saved
      List<Translation> translationsToSave = new ArrayList<Translation>();
      translationMergeDtos.forEach((TranslationMergeDto mergeDto) -> {
        mergeDto
          .getTranslations()
          .forEach(
            (TranslationRecord r) -> translationsToSave
              .add(
                new Translation()
                  .setEntityName(mergeDto.getKey().entityName())
                  .setEntityField(mergeDto.getKey().entityField())
                  .setEntityId(mergeDto.getEntityId())
                  .setLanguage(r.language())
                  .setText(r.text())
              )
          );
      });

      // retrieve all existing translations
      List<Translation> existingTranslations = translationRepository.findTranslationsForMerge(translationMergeDtos);

      // build a list of TranslationId's to save
      Set<TranslationId> idsToSave = translationsToSave
        .stream()
        .map(
          (Translation t) -> new TranslationId()
            .setEntityName(t.getEntityName())
            .setEntityField(t.getEntityField())
            .setEntityId(t.getEntityId())
            .setLanguage(t.getLanguage())
        )
        .collect(Collectors.toSet());

      // remove from existing translations what is not in the to save list
      List<Translation> translationsToDelete = existingTranslations
        .stream()
        .filter(
          (Translation t) -> !idsToSave
            .contains(
              new TranslationId()
                .setEntityName(t.getEntityName())
                .setEntityField(t.getEntityField())
                .setEntityId(t.getEntityId())
                .setLanguage(t.getLanguage())
            )
        )
        .collect(Collectors.toList());      

      translationRepository.deleteAll(translationsToDelete);
      result = ResultDto
        .success(
          translationRepository
            .saveAll(translationsToSave)
            .stream()
            .map((Translation t) -> new TranslationDto(t))
            .collect(Collectors.toList())
        );
    }
    return result;
  }
  //#endregion
}
