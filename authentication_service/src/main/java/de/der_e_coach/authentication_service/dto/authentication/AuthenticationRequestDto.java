package de.der_e_coach.authentication_service.dto.authentication;

public class AuthenticationRequestDto {
  // #region Private fields ---------------------------------------------------
  private String user;
  private String password;
  // #endregion

  // #region Constructor ------------------------------------------------------
  public AuthenticationRequestDto() {
  }
  // #endregion

  // #region Getters/Setters --------------------------------------------------
  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
  // #endregion
}
