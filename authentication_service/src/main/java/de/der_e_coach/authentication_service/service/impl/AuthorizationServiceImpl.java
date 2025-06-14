package de.der_e_coach.authentication_service.service.impl;

import org.springframework.stereotype.Service;

import de.der_e_coach.authentication_service.service.AuthorizationService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthorizationResultDto;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
  // #region Private fields ---------------------------------------------------
  private final JwtTokenValidatorImpl jwtTokenValidator;
  // #endregion

  // #region Constructor ------------------------------------------------------
  public AuthorizationServiceImpl(JwtTokenValidatorImpl jwtTokenValidator) {
    this.jwtTokenValidator = jwtTokenValidator;
  }
  // #endregion

  // #region AuthorizationService members -------------------------------------
  @Override
  public ResultDto<AuthorizationResultDto> authorize(String token) {
    ResultDto<AuthorizationResultDto> result;
    try {
      result = jwtTokenValidator.getAuthentication(token);
    } catch (Exception e) {
      result = ResultDto.unauthorized();
    }
    return result;
  }
  //#endregion
}
