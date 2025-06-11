package de.der_e_coach.shared_lib.dto.system;

public class ApiInfoDto {
  //#region Private fields ----------------------------------------------------
  private String version;
  private String environment;
  private String name;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public ApiInfoDto() {
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public String getVersion() {
    return version;
  }

  public String getEnvironment() {
    return environment;
  }

  public ApiInfoDto setVersion(String version) {
    this.version = version;
    return this;
  }

  public ApiInfoDto setEnvironment(String environment) {
    this.environment = environment;
    return this;
  }

  public String getName() {
    return name;
  }

  public ApiInfoDto setName(String name) {
    this.name = name;
    return this;
  }
  //#endregion  
}
