package de.der_e_coach.minimal_service.service.feign.authentication_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import de.der_e_coach.shared_lib.dto.authorization.AuthorizationResultDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

@FeignClient(name = "authentication-service", url = "${der-e-coach.authentication-service.url}")
public interface AuthenticationServiceClient {
  /**
   * Validate the JWT
   * 
   * @param token the JWT
   * @return {@link AuthorizationResultDto}
   */
  @PostMapping(
      path = "/authorize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResultDto<AuthorizationResultDto> validateToken(@RequestBody String token);
}
