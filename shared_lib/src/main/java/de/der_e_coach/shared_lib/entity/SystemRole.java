package de.der_e_coach.shared_lib.entity;

import java.util.List;

import de.der_e_coach.shared_lib.dto.translation.TranslationKeyDto;

public enum SystemRole {
    //#region Enum values -----------------------------------------------------
    SYS_ADMIN(true, "sys_admin", "SYS_ADMIN"),
    USER(false, "user", "USER");
  //#endregion

  //#region Private fields ----------------------------------------------------
  final private boolean systemAdministrator;
  final private TranslationKeyDto translationKey;
  final private String value;
  //#endregion

  //#region Constructor -------------------------------------------------------
  private SystemRole(boolean systemAdministrator, final String translationFieldName, final String value) {
    this.systemAdministrator = systemAdministrator;
    this.translationKey = new TranslationKeyDto("role", translationFieldName);
    this.value = value;
  }
  //#endregion

  //#region Static Methods ----------------------------------------------------
  public static String[] systemAdministratorValues() {
    return List
      .of(SystemRole.values())
      .stream()
      .filter((SystemRole s) -> s.isSystemAdministrator())
      .map((SystemRole s) -> s.getValue())
      .toArray(String[]::new);
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public TranslationKeyDto getTranslationKey() {
    return translationKey;
  }

  public String getValue() {
    return value;
  }

  public boolean isSystemAdministrator() {
    return systemAdministrator;
  }
  //#endregion
}
