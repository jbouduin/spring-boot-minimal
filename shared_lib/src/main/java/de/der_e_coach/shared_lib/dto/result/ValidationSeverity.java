package de.der_e_coach.shared_lib.dto.result;

import de.der_e_coach.shared_lib.dto.translation.TranslationKeyDto;

public enum ValidationSeverity {
    //#region Enum values -----------------------------------------------------
    Error("severity_error"),
    Warning("severity_warning"),
    Info("_severity_info");
  //#endregion

  //#region Private fields ----------------------------------------------------
  final private TranslationKeyDto translationKey;
  //#endregion

  //#region Constructor -------------------------------------------------------
  private ValidationSeverity(String entityField) {
    this.translationKey = new TranslationKeyDto("validation_severity", entityField);
  }
  //#endregion

    public TranslationKeyDto getTranslationKey() {
    return this.translationKey;
  }
}
