package de.der_e_coach.translation_service.entity;

import java.io.Serializable;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;

import de.der_e_coach.shared_lib.dto.translation.TranslationKeyDto;

/**
 * Composite primary key for the Translation entity
 */
public class TranslationId implements Serializable {
  //#region Entity fields -----------------------------------------------------
  private String entityName;
  private String entityField;
  private Long entityId;
  private LanguageCode language;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public TranslationId() {
  }

  public TranslationId(TranslationKeyDto key, Long entityId, LanguageCode language) {
    this.entityName = key.entityName();
    this.entityField = key.entityName();
    this.entityId = entityId;
    this.language = language;
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public String getEntityName() {
    return entityName;
  }

  public TranslationId setEntityName(String translationKey) {
    this.entityName = translationKey;
    return this;
  }

  public LanguageCode getLanguage() {
    return language;
  }

  public TranslationId setLanguage(LanguageCode language) {
    this.language = language;
    return this;
  }

  public Long getEntityId() {
    return entityId;
  }

  public TranslationId setEntityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }

  public String getEntityField() {
    return entityField;
  }

  public TranslationId setEntityField(String entityField) {
    this.entityField = entityField;
    return this;
  }
  //#endregion

  //#region overrides ---------------------------------------------------------
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((entityName == null) ? 0 : entityName.hashCode());
    result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof TranslationId))
      return false;
    TranslationId other = (TranslationId)obj;
    if (entityName == null) {
      if (other.entityName != null)
        return false;
    } else if (!entityName.equals(other.entityName))
      return false;
    if (entityId == null) {
      if (other.entityId != null)
        return false;
    } else if (!entityId.equals(other.entityId))
      return false;
    if (language == null) {
      if (other.language != null)
        return false;
    } else if (!language.equals(other.language))
      return false;
    return true;
  }
  // #endregion
}
