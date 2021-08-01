package com.epam.digital.data.platform.starter.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.security.config.Whitelist;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationFilterTest {

  @Mock
  private HttpServletRequest httpServletRequest;
  @Mock
  private HttpServletResponse httpServletResponse;
  @Mock
  private FilterChain filterChain;

  @Mock
  private TokenProvider tokenProvider;

  @Mock
  private Whitelist whitelist;

  private JwtAuthenticationFilter filter;

  @Before
  public void init() {
    filter = new JwtAuthenticationFilter(tokenProvider, whitelist);
  }

  @After
  public void tearDown() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  public void shouldSetAuthentication() throws ServletException, IOException {
    when(httpServletRequest.getHeader(JwtAuthenticationFilter.AUTHORIZATION_HEADER))
        .thenReturn("token");
    User principal = new User("username", "", Collections.emptyList());
    Authentication auth = new UsernamePasswordAuthenticationToken(principal,
        "token", Collections.emptyList());
    when(tokenProvider.getAuthentication("token")).thenReturn(auth);

    filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertThat(authentication).isNotNull();
    assertThat(authentication.getName()).isEqualTo("username");
  }

  @Test
  public void shouldNotSetAuthentication() throws ServletException, IOException {
    filter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    assertThat(authentication).isNull();
  }
}