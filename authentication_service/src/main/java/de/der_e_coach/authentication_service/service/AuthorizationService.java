package de.der_e_coach.authentication_service.service;

import de.der_e_coach.shared_lib.dto.authorization.AuthorizationResultDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

public interface AuthorizationService {
  ResultDto<AuthorizationResultDto> authorize(String token);
}
