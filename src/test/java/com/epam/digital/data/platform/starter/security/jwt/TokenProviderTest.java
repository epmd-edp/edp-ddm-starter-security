package com.epam.digital.data.platform.starter.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenProviderTest {

  private final TokenProvider tokenProvider = new TokenProvider(
      new TokenParser(new ObjectMapper()));

  @Test
  public void shouldCreateAuthenticationBasedOnJwtToken() throws IOException {
    var token = new String(ByteStreams.toByteArray(
        TokenProviderTest.class.getResourceAsStream("/json/officerAccessToken.json")));

    var authentication = tokenProvider.getAuthentication(token);

    assertThat(authentication).isNotNull();
    assertThat(authentication.getName()).isEqualTo("testuser");
    assertThat(authentication.getAuthorities()).isNotEmpty();
    var authorities = authentication.getAuthorities();
    assertThat(authorities.stream().anyMatch(a -> "officer".equals(a.getAuthority()))).isTrue();
  }

  @Test
  public void shouldCreateAuthenticationWithoutAuthorities() throws IOException {
    var token = new String(ByteStreams.toByteArray(
        TokenProviderTest.class.getResourceAsStream("/json/simpleAccessToken.json")));

    var authentication = tokenProvider.getAuthentication(token);

    assertThat(authentication.getAuthorities()).isEmpty();
  }
}