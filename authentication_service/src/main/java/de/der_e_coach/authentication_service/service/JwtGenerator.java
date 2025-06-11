package de.der_e_coach.authentication_service.service;

import java.util.List;

public interface JwtGenerator {
  /**
   * Generate a JWT using the user name and assigned roles.
   * 
   * @param username
   * @param roles
   * @return the JWT
   */
  String generateToken(String username, List<String> roles);
}
