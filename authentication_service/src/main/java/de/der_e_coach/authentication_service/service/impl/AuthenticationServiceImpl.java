package de.der_e_coach.authentication_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import de.der_e_coach.authentication_service.dto.authentication.AuthenticationRequestDto;
import de.der_e_coach.authentication_service.dto.authentication.AuthenticationResponseDto;
import de.der_e_coach.authentication_service.service.AuthenticationService;
import de.der_e_coach.authentication_service.service.JwtGenerator;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  //#region Private fields ----------------------------------------------------
  private final JwtGenerator jwtGenerator;
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsManager userDetailsManager;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public AuthenticationServiceImpl(
    JwtGenerator jwtGenerator,
    PasswordEncoder passwordEncoder,
    UserDetailsManager userDetailsManager
  ) {
    this.jwtGenerator = jwtGenerator;
    this.passwordEncoder = passwordEncoder;
    this.userDetailsManager = userDetailsManager;
  }
  //#endregion

  //#region AuthenticationService members -------------------------------------
  public ResultDto<AuthenticationResponseDto> login(AuthenticationRequestDto requestDto) {
    ResultDto<AuthenticationResponseDto> result = null;
    UserDetails userDetails = null;
    try {
      userDetails = userDetailsManager.loadUserByUsername(requestDto.getUser());
    } catch (UsernameNotFoundException e) {
      result = ResultDto.unauthorized();
      result.setErrors("Invalid username or password");
    }
    if (userDetails != null) {
      if (!passwordEncoder.matches(requestDto.getPassword(), userDetails.getPassword())) {
        result = ResultDto.unauthorized();
        result.setErrors("Invalid username or password");
      } else {
        List<String> roles = userDetails
          .getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
        result = ResultDto
          .success(
            new AuthenticationResponseDto().setToken(jwtGenerator.generateToken(userDetails.getUsername(), roles))
          );
      }
    }
    return result;
  }
  // #endregion
}
