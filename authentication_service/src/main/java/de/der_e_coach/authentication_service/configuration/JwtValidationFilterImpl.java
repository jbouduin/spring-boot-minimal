package de.der_e_coach.authentication_service.configuration;

import org.springframework.stereotype.Component;

import de.der_e_coach.authentication_service.service.AuthorizationService;
import de.der_e_coach.shared_lib.configuration.BaseJwtValidationFilter;
import de.der_e_coach.shared_lib.configuration.JwtValidationFilter;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthorizationResultDto;

@Component
public class JwtValidationFilterImpl extends BaseJwtValidationFilter implements JwtValidationFilter {
  //#region Private fields ----------------------------------------------------
  private final AuthorizationService authorizationService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public JwtValidationFilterImpl(AuthorizationService authorizationService) {
    this.authorizationService = authorizationService;
  }
  //#endregion

  //#region BaseJwtValidationFilter Members -----------------------------------
  @Override
  protected ResultDto<AuthorizationResultDto> authorize(String token) {
    return authorizationService.authorize(token);
  }
  //#endregion
}
