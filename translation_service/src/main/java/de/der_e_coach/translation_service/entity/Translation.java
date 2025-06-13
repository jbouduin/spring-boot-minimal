package de.der_e_coach.translation_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.shared_lib.dto.translation.TranslationDto;
import de.der_e_coach.shared_lib.dto.translation.TranslationKeyDto;

@Entity
@IdClass(TranslationId.class)
public class Translation {
  //#region Static ------------------------------------------------------------
  public final static Long NONE_ENTITY_ID = -1L;
  //#endregion

  //#region Entity field - entityName -----------------------------------------
  @Id
  @Column(name = "entity_name")
  @NotNull(message = "The entity name must have a value.")
  @NotEmpty(message = "The entity name must have a value.")
  private String entityName;
  //#endregion

  //#region Entity field - entityField ----------------------------------------
  @Id
  @Column(name = "entity_field")
  @NotNull(message = "The entity field must have a value.")
  @NotEmpty(message = "The entity field must have a value.")
  private String entityField;
  //#endregion

  //#region Entity field - entityId -------------------------------------------
  @Id
  @Column(name = "entity_id")
  @NotNull(message = "The entity Id must have a value.")
  private Long entityId;
  //#endregion

  //#region Entity field - languagecode ---------------------------------------
  @Id
  @Enumerated
  @JdbcType(PostgreSQLEnumJdbcType.class)
  @Column(name = "language_code")
  private LanguageCode language;
  //#endregion

  //#region Entity field - text -----------------------------------------------
  @Column(name = "translation_text")
  @NotNull(message = "The translation must have a value.")
  @NotEmpty(message = "The translation must have a value.")
  private String text;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public Translation() {
  }

  public Translation(TranslationKeyDto translationKey, Long entityId, LanguageCode language, String text) {
    this.entityName = translationKey.entityName();
    this.entityField = translationKey.entityField();
    this.entityId = entityId;
    this.language = language;
    this.text = text;
  }

  public Translation(TranslationDto dto) {
    this.entityName = dto.getKey().entityName();
    this.entityField = dto.getKey().entityField();
    this.entityId = dto.getEntityId();
    this.language = dto.getLanguageCode();
    this.text = dto.getText();
  }

  public Translation(TranslationKeyDto TranslationKeyDto, LanguageCode languageCode, String text) {
    this.entityName = TranslationKeyDto.entityName();
    this.entityField = TranslationKeyDto.entityField();
    this.entityId = NONE_ENTITY_ID;
    this.language = languageCode;
    this.text = text;
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public LanguageCode getLanguage() {
    return language;
  }

  public Translation setLanguage(LanguageCode languageCode) {
    this.language = languageCode;
    return this;
  }

  public String getText() {
    return text;
  }

  public Translation setText(String text) {
    this.text = text;
    return this;
  }

  public Long getEntityId() {
    return entityId;
  }

  public Translation setEntityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }

  public String getEntityName() {
    return entityName;
  }

  public Translation setEntityName(String entityName) {
    this.entityName = entityName;
    return this;
  }

  public String getEntityField() {
    return entityField;
  }

  public Translation setEntityField(String entityField) {
    this.entityField = entityField;
    return this;
  }
  //#endregion

  //#region Overrides ---------------------------------------------------------
  @Override
  public String toString() {
    return "Translation [entityName=" + entityName + ", entityField=" + entityField + ", entityId=" + entityId
      + ", language=" + language + ", text=" + text + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((entityName == null) ? 0 : entityName.hashCode());
    result = prime * result + ((entityField == null) ? 0 : entityField.hashCode());
    result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
    result = prime * result + ((language == null) ? 0 : language.hashCode());
    result = prime * result + ((text == null) ? 0 : text.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Translation other = (Translation)obj;
    if (entityName == null) {
      if (other.entityName != null)
        return false;
    } else if (!entityName.equals(other.entityName))
      return false;
    if (entityField == null) {
      if (other.entityField != null)
        return false;
    } else if (!entityField.equals(other.entityField))
      return false;
    if (entityId == null) {
      if (other.entityId != null)
        return false;
    } else if (!entityId.equals(other.entityId))
      return false;
    if (language != other.language)
      return false;
    if (text == null) {
      if (other.text != null)
        return false;
    } else if (!text.equals(other.text))
      return false;
    return true;
  }
  //#endregion  
}
