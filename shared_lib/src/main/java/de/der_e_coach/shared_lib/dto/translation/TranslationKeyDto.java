package de.der_e_coach.shared_lib.dto.translation;

public record TranslationKeyDto(String entityName, String entityField) {
  // public static TranslationKeyDto from(TranslatableEnum e) {
  //   return new TranslationKeyDto(e.getEntityName(), e.getEntityField());
  // }
}
