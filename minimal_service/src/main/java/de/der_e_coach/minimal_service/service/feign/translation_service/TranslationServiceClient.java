package de.der_e_coach.minimal_service.service.feign.translation_service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationDto;

@FeignClient(name = "translation-service", url = "${der-e-coach.translation-service.url}")
public interface TranslationServiceClient {
  /**
   * Create new translations
   * 
   * @param  translations List of {@link TranslationDto}
   * @return              the created translations
   */
  @PostMapping(
      path = "/translation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResultDto<List<TranslationDto>> createTranslations(List<TranslationDto> translations);
  /**
   * Get translatios
   * 
   * @param  entityNames  List of entity names
   * @param  entityFields List of entity fields
   * @param  entityIds    List of entity id's
   * @param  languages    List of languages
   * @return              the list of {@link TranslationDto}
   */
  @GetMapping(path = "/translation", produces = MediaType.APPLICATION_JSON_VALUE)
  ResultDto<List<TranslationDto>> getTranslations(
    @RequestParam(name = "entities", required = false) List<String> entityNames,
    @RequestParam(name = "fields", required = false) List<String> entityFields,
    @RequestParam(name = "ids", required = false) List<Long> entityIds,
    @RequestParam(name = "lang", required = false) List<LanguageCode> languages
  );
  /**
   * Create, Update and Delete translations.
   * 
   * @param  translationMergeDtos A list of {@link TranslationMergeDto}
   * @return
   */
  @PostMapping(path = "/translation/merge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResultDto<List<TranslationDto>> mergeTranslations(@RequestBody List<TranslationMergeDto> translationMergeDtos);
}
