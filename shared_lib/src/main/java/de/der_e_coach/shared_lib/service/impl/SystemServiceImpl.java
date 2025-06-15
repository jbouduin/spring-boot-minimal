package de.der_e_coach.shared_lib.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.system.ApiInfoDto;
import de.der_e_coach.shared_lib.service.SystemService;

@Service
public class SystemServiceImpl implements SystemService {
  //#region Private injected fields -------------------------------------------
  @Value("${spring.application.version}")
  private String version;
  @Value("${spring.application.name}")
  private String name;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public SystemServiceImpl() {
  }
  //#endregion

  //#region SystemService Members ---------------------------------------------
  @Override
  public ResultDto<ApiInfoDto> getApiInfo() {
    ApiInfoDto result = new ApiInfoDto()
      .setVersion(version)
      .setEnvironment("To do")
      .setName(name);
    return ResultDto.success(result);
  }
  //#endregion
}
