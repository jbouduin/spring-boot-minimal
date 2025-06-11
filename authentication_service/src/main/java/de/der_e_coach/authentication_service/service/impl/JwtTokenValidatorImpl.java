package de.der_e_coach.authentication_service.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.der_e_coach.authentication_service.service.JwtTokenValidator;
import de.der_e_coach.shared_lib.dto.authorization.AuthorizationResultDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidatorImpl implements JwtTokenValidator {
  // #region Private fields ---------------------------------------------------
  private final PublicKey publicKey;
  // #endregion

  // #region Constructor ------------------------------------------------------
  public JwtTokenValidatorImpl(
      @Value("${der-e-coach.jwt.public-key-path}") String publicKeyPath) throws Exception {
    this.publicKey = loadPublicKey(publicKeyPath);
  }
  // #endregion

  // #region JwtTokenValidator members ----------------------------------------
  public ResultDto<AuthorizationResultDto> getAuthentication(String token)
      throws JsonProcessingException, ExpiredJwtException {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(publicKey)
        .build()
        .parseClaimsJws(token)
        .getBody();

    ObjectMapper mapper = new ObjectMapper();
    String rolesJson = mapper.writeValueAsString(claims.get("roles"));
    List<String> roles = mapper.readValue(
        rolesJson,
        new TypeReference<ArrayList<String>>() {
        });

    AuthorizationResultDto authorizationResult = new AuthorizationResultDto().setRoles(roles)
        .setAccountName(claims.getSubject());
    return ResultDto.success(authorizationResult);
  }
  // #endregion

  // #region Auxiliary methods ------------------------------------------------
  private PublicKey loadPublicKey(String path) throws Exception {
    String key = Files.readString(Paths.get(path))
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replaceAll("\\s", "");
    byte[] decoded = Base64.getDecoder().decode(key);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
    return KeyFactory.getInstance("RSA").generatePublic(spec);
  }
  // #endregion
}
