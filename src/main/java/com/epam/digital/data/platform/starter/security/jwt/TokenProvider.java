package com.epam.digital.data.platform.starter.security.jwt;

import com.epam.digital.data.platform.starter.security.dto.JwtClaimsDto;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Component that is used for building {@link Authentication} from string JWT token
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

  private final TokenParser tokenParser;

  /**
   * Building {@link Authentication} object from string JWT token
   */
  public Authentication getAuthentication(String token) {
    log.debug("Start parsing access token");
    var claims = tokenParser.parseClaims(token);
    log.debug("Access token decoded successfully");

    var authorities = getAuthorities(claims);
    var principal = new User(claims.getPreferredUsername(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  private Collection<GrantedAuthority> getAuthorities(JwtClaimsDto claimsDto) {
    if (Objects.isNull(claimsDto.getRealmAccess())
        || CollectionUtils.isEmpty(claimsDto.getRealmAccess().getRoles())) {
      return Collections.emptyList();
    }

    return claimsDto.getRealmAccess().getRoles()
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }
}
