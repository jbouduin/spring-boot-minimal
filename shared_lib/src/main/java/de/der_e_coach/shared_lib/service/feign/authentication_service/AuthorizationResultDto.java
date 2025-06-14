package de.der_e_coach.shared_lib.service.feign.authentication_service;

import java.util.List;

public class AuthorizationResultDto {
  //#region Private fields ----------------------------------------------------
  private String accountName;
  private List<String> roles;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public AuthorizationResultDto() {
  }
  //#endregion

  //#region Getters/Setters ---------------------------------------------------
  public String getAccountName() {
    return accountName;
  }

  public AuthorizationResultDto setAccountName(String accountName) {
    this.accountName = accountName;
    return this;
  }

  public List<String> getRoles() {
    return roles;
  }

  public AuthorizationResultDto setRoles(List<String> roles) {
    this.roles = roles;
    return this;
  }

  @Override
  public String toString() {
    return "AuthorizationResultDto [accountName=" + accountName + ", roles=" + roles + "]";
  }
  //#endregion
}
