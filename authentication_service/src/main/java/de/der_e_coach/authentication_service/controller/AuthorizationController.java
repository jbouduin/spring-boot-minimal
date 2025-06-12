package de.der_e_coach.authentication_service.controller;

import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.authentication_service.service.AuthorizationService;
import de.der_e_coach.shared_lib.dto.authorization.AuthorizationResultDto;
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
@Tag(name = "Authorization")
public class AuthorizationController {
  //#region Private fields ----------------------------------------------------
  private AuthorizationService authorizationController;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public AuthorizationController(AuthorizationService authorizationService) {
    this.authorizationController = authorizationService;
  }
  // #endregion

  //#region Post "authorize" --------------------------------------------------
  @Operation(summary = "Authorize", description = "Checks the JWT passed and extracts account name and roles.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "403", description = "Not authorized. Currently without distinction on the cause.")
      }
  )
  @PostMapping(
      path = "authorize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ResultDto<AuthorizationResultDto>> authorize(@RequestBody String token) {
    ResultDto<AuthorizationResultDto> result = authorizationController.authorize(token);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  // #endregion
}
