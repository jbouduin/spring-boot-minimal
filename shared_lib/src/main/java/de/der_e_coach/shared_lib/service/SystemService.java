package de.der_e_coach.shared_lib.service;

import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.dto.system.ApiInfoDto;

public interface SystemService {
/**
   * Get API info about the service.
   * 
   * @return {@link ApiInfoDto}
   */
  ResultDto<ApiInfoDto> getApiInfo();
}
