package com.epam.digital.data.platform.starter.security.jwt;

import com.epam.digital.data.platform.starter.security.dto.JwtClaimsDto;
import com.epam.digital.data.platform.starter.security.exception.JwtParsingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Component that is used for parsing {@link JwtClaimsDto} from string JWT token
 */
@Component
@RequiredArgsConstructor
public class TokenParser {

  private final ObjectMapper objectMapper;

  /**
   * Parsing {@link JwtClaimsDto} from string JWT token
   *
   * @param token string JWT token
   * @return dto of JWT claims
   */
  public JwtClaimsDto parseClaims(String token) {
    try {
      var signedJWT = SignedJWT.parse(token);
      return objectMapper.readValue(signedJWT.getPayload().toString(), JwtClaimsDto.class);
    } catch (ParseException | JsonProcessingException e) {
      throw new JwtParsingException(e.getMessage());
    }
  }
}
