package com.epam.digital.data.platform.starter.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.digital.data.platform.starter.security.dto.enums.SubjectType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenParserTest {

  private final TokenParser tokenParser = new TokenParser(new ObjectMapper());

  @Test
  public void shouldGetUserAttributesFromJwtToken() throws IOException {
    var token = new String(ByteStreams.toByteArray(
        TokenProviderTest.class.getResourceAsStream("/json/officerAccessToken.json")));

    var jwtClaimsDto = tokenParser.parseClaims(token);

    assertThat(jwtClaimsDto).isNotNull();
    assertThat(jwtClaimsDto.isRepresentative()).isTrue();
    assertThat(jwtClaimsDto.getSubjectType()).isEqualTo(SubjectType.INDIVIDUAL);
  }
}
