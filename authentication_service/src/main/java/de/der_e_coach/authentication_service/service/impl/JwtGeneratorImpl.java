package de.der_e_coach.authentication_service.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.der_e_coach.authentication_service.service.JwtGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGeneratorImpl implements JwtGenerator {
  // #region Private fields ---------------------------------------------------
  private final long expirationTime;
  private final PrivateKey privateKey;
  // #endregion

  // #region Constructor ------------------------------------------------------
  public JwtGeneratorImpl(
      @Value("${der-e-coach.jwt.private-key-path}") String privateKeyPath,
      @Value("${der-e-coach.jwt.time-out}") long expirationTime) {
    this.expirationTime = expirationTime;
    this.privateKey = loadPrivateKey(privateKeyPath);
  }

  // #region JwtGenerator members ---------------------------------------------
  public String generateToken(String username, List<String> roles) {
    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(privateKey, SignatureAlgorithm.RS256)
        .compact();
  }
  // #endregion

  // #region Auxiliary methods ------------------------------------------------
  private PrivateKey loadPrivateKey(String privateKeyPath) {
    try {
      String key = Files.readString(Paths.get(privateKeyPath))
          .replace("-----BEGIN PRIVATE KEY-----", "")
          .replace("-----END PRIVATE KEY-----", "")
          .replaceAll("\\s", "");
      byte[] decoded = Base64.getDecoder().decode(key);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePrivate(keySpec);

    } catch (Exception e) {
      throw new RuntimeException("Failed to load public key", e);
    }
  }
  // #endregion
}
