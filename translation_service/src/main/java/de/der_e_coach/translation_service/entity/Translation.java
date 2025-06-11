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
  private LanguageCode languageCode;
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

  public Translation(TranslationKeyDto translationKey, Long entityId, LanguageCode languageCode, String text) {
    this.entityName = translationKey.entityName();
    this.entityField = translationKey.entityField();
    this.entityId = entityId;
    this.languageCode = languageCode;
    this.text = text;
  }

  public Translation(TranslationDto dto) {
    this.entityName = dto.getEntityName();
    this.entityField = dto.getEntityField();
    this.entityId = dto.getEntityId();
    this.languageCode = dto.getLanguageCode();
    this.text = dto.getText();
  }

  public Translation(TranslationKeyDto TranslationKeyDto, LanguageCode languageCode, String text) {
    this.entityName = TranslationKeyDto.entityName();
    this.entityField = TranslationKeyDto.entityField();
    this.entityId = 0L;
    this.languageCode = languageCode;
    this.text = text;
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public LanguageCode getLanguageCode() {
    return languageCode;
  }

  public Translation setLanguageCode(LanguageCode languageCode) {
    this.languageCode = languageCode;
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
}
