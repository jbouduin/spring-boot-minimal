package de.der_e_coach.authentication_service.service;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthorizationResultDto;

public interface AuthorizationService {
  ResultDto<AuthorizationResultDto> authorize(String token);
}
