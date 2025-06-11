package de.der_e_coach.authentication_service.dto.authentication;

public class AuthenticationResponseDto {
  // #region Private fields ---------------------------------------------------
  private String token;
  // #endregion

  // #region Constructor ------------------------------------------------------
  public AuthenticationResponseDto() {
  }
  // #endregion

  // #region Getters/Setters --------------------------------------------------
  public String getToken() {
    return token;
  }

  public AuthenticationResponseDto setToken(String token) {
    this.token = token;
    return this;
  }
  // #endregion
}
