package com.epam.digital.data.platform.starter.security.jwt;

import com.epam.digital.data.platform.starter.security.config.Whitelist;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION_HEADER = "x-access-token";

  private final TokenProvider tokenProvider;
  private final Whitelist whitelist;

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwt = resolveToken(request);

    if (StringUtils.hasText(jwt)) {
      Authentication authentication = tokenProvider.getAuthentication(jwt);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      log.warn("no valid JWT token found, uri: {}", request.getRequestURI());
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return whitelist.getRequestMatcher().matches(request);
  }

  private String resolveToken(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION_HEADER);
    return Objects.isNull(token) ? Strings.EMPTY : token;
  }
}
