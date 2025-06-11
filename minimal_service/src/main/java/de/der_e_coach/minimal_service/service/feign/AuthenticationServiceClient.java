package de.der_e_coach.minimal_service.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import de.der_e_coach.shared_lib.dto.authorization.AuthorizationResultDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "authentication-service", url = "http://localhost:5401/api")
public interface AuthenticationServiceClient {

  @PostMapping("/authorize")
  ResultDto<AuthorizationResultDto> validateToken(@RequestBody String token);
  
}
