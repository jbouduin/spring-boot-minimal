package de.der_e_coach.translation_service.service;

import java.util.List;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.translation_service.dto.TranslationDto;

public interface TranslationService {
  ResultDto<List<TranslationDto>> createTranslations(List<TranslationDto> translations);
}
