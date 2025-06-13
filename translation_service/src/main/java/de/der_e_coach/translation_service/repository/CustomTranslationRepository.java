package de.der_e_coach.translation_service.repository;

import java.util.List;

import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.translation_service.entity.Translation;

public interface CustomTranslationRepository {
  List<Translation> findTranslationsForMerge(List<TranslationMergeDto> translationMergeDtos);
}
