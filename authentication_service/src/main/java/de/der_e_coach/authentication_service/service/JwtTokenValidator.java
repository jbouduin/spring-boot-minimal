package de.der_e_coach.authentication_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.der_e_coach.shared_lib.dto.authorization.AuthorizationResultDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtTokenValidator {
  /**
   * Validate the given JWT and extract account name and roles.
   * 
   * @param token
   * @return
   */
  ResultDto<AuthorizationResultDto> getAuthentication(String token)
      throws JsonProcessingException, ExpiredJwtException;
}
