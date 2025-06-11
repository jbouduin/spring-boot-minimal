package de.der_e_coach.shared_lib.dto.result;

import de.der_e_coach.shared_lib.dto.translation.TranslatableEnum;

public enum ValidationSeverity implements TranslatableEnum {
    //#region Enum values -----------------------------------------------------
    Error("severity_error"),
    Warning("severity_warning"),
    Info("_severity_info");
  //#endregion

  //#region Private fields ----------------------------------------------------
  final private String entityField;
  //#endregion

  //#region Constructor -------------------------------------------------------
  private ValidationSeverity(String entityField) {
    this.entityField = entityField;
  }
  //#endregion

  //#region TranslatableEnum members ------------------------------------------
  @Override
  public String getEntityName() {
    return "validation";
  }

  @Override
  public String getEntityField() {
    return entityField;
  }
  //#endregion
}
