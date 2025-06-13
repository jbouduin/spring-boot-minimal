package de.der_e_coach.shared_lib.dto.translation;

import java.util.ArrayList;
import java.util.List;

public class TranslationMergeDto {
  //#region Private fields ----------------------------------------------------
  private Long entityId;
  private TranslationKeyDto key;
  private List<TranslationRecord> translations;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public TranslationMergeDto() {
    this.translations = new ArrayList<TranslationRecord>();
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public Long getEntityId() {
    return entityId;
  }

  public TranslationMergeDto setEntityId(Long entityId) {
    this.entityId = entityId;
    return this;
  }

  public TranslationKeyDto getKey() {
    return key;
  }

  public TranslationMergeDto setKey(TranslationKeyDto key) {
    this.key = key;
    return this;
  }

  public List<TranslationRecord> getTranslations() {
    return translations;
  }

  public TranslationMergeDto setTranslations(List<TranslationRecord> translations) {
    this.translations = translations;
    return this;
  }
  //#endregion
}
