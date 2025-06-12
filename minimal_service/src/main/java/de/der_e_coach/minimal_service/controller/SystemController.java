package de.der_e_coach.minimal_service.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.minimal_service.service.SystemService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.system.ApiInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "System")
@RequestMapping(path = "/system")
public class SystemController {
  //#region Private fields ----------------------------------------------------
  private final SystemService systemService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public SystemController(SystemService systemService) {
    this.systemService = systemService;
  }
  //#endregion

  //#region Get "info" --------------------------------------------------------
  @Operation(summary = "Info", description = "Get information abouth the service.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success")
      }
  )
  @GetMapping(path = "info", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<ApiInfoDto>> getServiceVersion() {
    ResultDto<ApiInfoDto> result = systemService.getApiInfo();
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion
}
