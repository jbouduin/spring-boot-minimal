package de.der_e_coach.shared_lib.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthenticationServiceClient;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthorizationResultDto;

@Component
public class JwtValidationFilter extends BaseJwtValidationFilter {
  //#region Private fields ----------------------------------------------------
  private AuthenticationServiceClient authenticationServiceClient;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public JwtValidationFilter(AuthenticationServiceClient authenticationServiceClient) {
    this.authenticationServiceClient = authenticationServiceClient;
  }
  //#endregion

  //#region BaseJwtValidationFilter Members -----------------------------------  
  @Override
  protected ResultDto<AuthorizationResultDto> authorize(String token) {
    return authenticationServiceClient.validateToken(token);
  }
  //#endregion
}
