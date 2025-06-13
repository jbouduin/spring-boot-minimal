package de.der_e_coach.translation_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.translation_service.dto.TranslationDto;
import de.der_e_coach.translation_service.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Translations")
@RequestMapping("translation")
public class TranslationController {
  //#region Private fields ----------------------------------------------------
  private final TranslationService translationService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public TranslationController(TranslationService translationService) {
    this.translationService = translationService;
  }
  //#endregion

  //#region Get ---------------------------------------------------------------
  @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<List<TranslationDto>>> getTranslations(
    @RequestParam(name = "entities", required = false) List<String> entityNames,
    @RequestParam(name = "fields", required = false) List<String> entityFields,
    @RequestParam(name = "ids", required = false) List<Long> entityIds,
    @RequestParam(name = "lang", required = false) List<LanguageCode> languages) {
    ResultDto<List<TranslationDto>> result = translationService.getTranslations(entityNames, entityFields, entityIds, languages);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Post --------------------------------------------------------------
  @Operation(summary = "Create translations", description = "Create Translations")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode="401",description="Authentication needed"),
        @ApiResponse(responseCode = "403", description = "Not authorized")
      }
  )
  @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<List<TranslationDto>>> createTranslations(
    @RequestBody List<TranslationDto> translations
  ) {
    ResultDto<List<TranslationDto>> result = translationService.createTranslations(translations);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Post --------------------------------------------------------------
  @Operation(summary = "Merge translations", description = "Update, create and delete translations in one go.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Authentication needed"),
        @ApiResponse(responseCode = "403", description = "Not authorized")
      }
  )
  @PostMapping(path = "/merge", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<List<TranslationDto>>> mergeTranslations(
    @RequestBody List<TranslationMergeDto> mergeTranslations
  ) {    
    ResultDto<List<TranslationDto>> result = translationService.mergeTranslations(mergeTranslations);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion
}
