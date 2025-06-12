package de.der_e_coach.authentication_service.controller;

import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.authentication_service.dto.authentication.AuthenticationRequestDto;
import de.der_e_coach.authentication_service.dto.authentication.AuthenticationResponseDto;
import de.der_e_coach.authentication_service.service.AuthenticationService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Tag(name = "Authentication")
public class AuthenticationController {
  //#region Private fields ----------------------------------------------------
  private final AuthenticationService authenticationService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }
  //#endregion

  //#region Post "login" ------------------------------------------------------
  @Operation(summary = "Login", description = "Login using account name or login, and password")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "401", description = "Invalid username or password")
      }
  )
  @PostMapping(path = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<AuthenticationResponseDto>> postMethodName(
    @RequestBody AuthenticationRequestDto requestDto
  ) {
    ResultDto<AuthenticationResponseDto> result = authenticationService.login(requestDto);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion
}
