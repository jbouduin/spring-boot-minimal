package de.der_e_coach.shared_lib.dto.translation;

/**
 * The interface for enum's that have translatable display values.
 * <br>
 * Example:
 * 
 * <pre>
 * public enum LanguageCode implements TranslatableEnum {
 *     DE("lng_de"),
 *     EN("lng_en"),
 *     NL("lng_nl"),
 *     FR("lng_fr");
 *
 *   final private String entityField;
 *
 *   LanguageCode(String entityField) {
 *     this.entityField = entityField;
 *   }
 *
 *   &#64;Override
 *   public String getEntityName() {
 *     return "lng";
 *   }
 * 
 *   &#64;Override
 *   public String getEntityField() {
 *     return entityField;
 *   }
 * }
 * </pre>
 */
public interface TranslatableEnum {
  String getEntityName();
  String getEntityField();
}
