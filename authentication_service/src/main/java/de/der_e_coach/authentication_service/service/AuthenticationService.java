package de.der_e_coach.authentication_service.service;

import de.der_e_coach.authentication_service.dto.authentication.AuthenticationRequestDto;
import de.der_e_coach.authentication_service.dto.authentication.AuthenticationResponseDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

public interface AuthenticationService {

  ResultDto<AuthenticationResponseDto> login(AuthenticationRequestDto requestDto);
}
