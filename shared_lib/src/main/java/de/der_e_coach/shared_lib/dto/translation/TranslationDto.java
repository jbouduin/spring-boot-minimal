package de.der_e_coach.shared_lib.dto.translation;

public class TranslationDto {
  //#region Private fields ----------------------------------------------------
  private TranslationKeyDto key;
  private Long entityId;
  private LanguageCode languageCode;
  private String text;
  //#endregion

  //#region Constructor -------------------------------------------------------
  // public TranslationDto(TranslatableEnum key, Long entityId, LanguageCode languageCode, String text) {
  //   this.entityName = key.getEntityName();
  //   this.entityField = key.getEntityField();
  //   this.entityId = entityId;
  //   this.languageCode = languageCode;
  //   this.text = text;
  // }

  // public TranslationDto(TranslatableEnum key, LanguageCode languageCode, String text) {
  //   this.entityName = key.getEntityName();
  //   this.entityField = key.getEntityField();
  //   this.languageCode = languageCode;
  //   this.entityId = 0L;
  //   this.text = text;
  // }

  // public TranslationDto(String entityName, String entityField, Long entityId, LanguageCode languageCode, String text) {
  //   this.entityName = entityName;
  //   this.entityField = entityField;
  //   this.entityId = entityId;
  //   this.languageCode = languageCode;
  //   this.text = text;
  // }

  public TranslationDto(TranslationKeyDto key, Long entityId, LanguageCode languageCode, String text) {
    this.key = key;    
    this.entityId = entityId;
    this.languageCode = languageCode;
    this.text = text;
  }

  public TranslationDto() {
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  // public String getEntityName() {
  //   return entityName;
  // }

  // public TranslationDto setEntityName(String translationKey) {
  //   this.entityName = translationKey;
  //   return this;
  // }

  public Long getEntityId() {
    return entityId;
  }

  public TranslationDto setEntityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }
  // #endregion

  // public String getEntityField() {
  //   return entityField;
  // }

  // public TranslationDto setEntityField(String entityField) {
  //   this.entityField = entityField;
  //   return this;
  // }

  public LanguageCode getLanguageCode() {
    return languageCode;
  }

  public TranslationDto setLanguageCode(LanguageCode languageCode) {
    this.languageCode = languageCode;
    return this;
  }

  public String getText() {
    return text;
  }

  public TranslationDto setText(String text) {
    this.text = text;
    return this;
  }
  // #endregion

  public TranslationKeyDto getKey() {
    return key;
  }

  public TranslationDto setKey(TranslationKeyDto key) {
    this.key = key;
    return this;
  }

  @Override
  public String toString() {
    return "TranslationDto [key=" + key + ", entityId=" + entityId + ", languageCode=" + languageCode + ", text=" + text
      + "]";
  }
  
}
