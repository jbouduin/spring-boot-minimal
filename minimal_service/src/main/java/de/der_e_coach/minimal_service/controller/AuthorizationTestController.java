package de.der_e_coach.minimal_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Test authorizations")
@RequestMapping(path = "/authorization_test")
public class AuthorizationTestController {

  @GetMapping(path = "authenticated")
  public ResponseEntity<ResultDto<String>> getAuthenticated() {
    ResultDto<String> result = ResultDto.success("getAuthenticated");
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @GetMapping(path = "sys_admin_only")
  @PreAuthorize("hasRole('ROLE_SYS_ADMIN')")
  public ResponseEntity<ResultDto<String>> getSysAdminOnly() {
    ResultDto<String> result = ResultDto.success("getSysAdminOnly");
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @GetMapping(path = "site_admin_only")
  @PreAuthorize("hasRole('ROLE_SITE_ADMIN')")
  public ResponseEntity<ResultDto<String>> getSiteAdminOnly() {
    ResultDto<String> result = ResultDto.success("getSiteAdminOnly");
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @GetMapping(path = "sys_and_siteadmin")
  @PreAuthorize("hasAnyRole('ROLE_SYS_ADMIN','ROLE_SITE_ADMIN')")
  public ResponseEntity<ResultDto<String>> getSysAndSiteAdmin() {
    ResultDto<String> result = ResultDto.success("getSysAndSiteAdmin");
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  @GetMapping(path = "non_existing_role")
  @PreAuthorize("hasRole('ROLE_DOES_NOT_EXIST')")
  public ResponseEntity<ResultDto<String>> getRoleDoesNotExist() {
    ResultDto<String> result = ResultDto.success("getRoleDoesNotExist");
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
}
