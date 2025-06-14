package de.der_e_coach.minimal_service.configuration;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;


import de.der_e_coach.shared_lib.dto.result.ResultDto;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthenticationServiceClient;
import de.der_e_coach.shared_lib.service.feign.authentication_service.AuthorizationResultDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtValidationFilter extends OncePerRequestFilter {
  //#region Private fields ----------------------------------------------------
  private AuthenticationServiceClient authenticationServiceClient;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public JwtValidationFilter(AuthenticationServiceClient authenticationServiceClient) {
    this.authenticationServiceClient = authenticationServiceClient;
  }
  //#endregion

  //#region OncePerRequestFilter members --------------------------------------
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException,
    IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      ResultDto<AuthorizationResultDto> validationResult = authenticationServiceClient.validateToken(token);      
      if (validationResult.isSuccess()) {
        Collection<? extends GrantedAuthority> authorities = validationResult
          .getData()
          .getRoles()
          .stream()
          .map((String role) -> new SimpleGrantedAuthority(role))
          .toList();
        SecurityContextHolder
          .getContext()
          .setAuthentication(
            new UsernamePasswordAuthenticationToken(validationResult.getData().getAccountName(), null, authorities)
          );
      } else {
        response.setStatus(validationResult.getStatus().getValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(validationResult));
        response.getWriter().flush();
      }
    }
    filterChain.doFilter(request, response);
  }
  //#endregion
}
