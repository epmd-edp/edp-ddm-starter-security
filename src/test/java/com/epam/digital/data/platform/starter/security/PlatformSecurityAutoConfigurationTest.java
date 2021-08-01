package com.epam.digital.data.platform.starter.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.digital.data.platform.starter.security.jwt.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

public class PlatformSecurityAutoConfigurationTest {

  private final WebApplicationContextRunner contextRunner = new WebApplicationContextRunner()
      .withBean(ObjectMapper.class)
      .withConfiguration(AutoConfigurations.of(PermitAllSecurityAutoConfiguration.class,
          PlatformSecurityAutoConfiguration.class));

  @Test
  public void shouldLoadPlatformSecurityAutoConfiguration() {
    contextRunner.run(context -> {
      assertThat(context).hasSingleBean(PlatformSecurityAutoConfiguration.class);
      assertThat(context).doesNotHaveBean(PermitAllSecurityAutoConfiguration.class);
      assertThat(context).hasSingleBean(WebSecurityConfig.class);
      assertThat(context).hasSingleBean(JwtAuthenticationFilter.class);
    });
  }

  @Test
  public void shouldLoadPermitAllSecurityAutoConfiguration() {
    contextRunner.withPropertyValues(
        "platform.security.enabled=false"
    )
    .run(context -> {
      assertThat(context).hasSingleBean(PermitAllSecurityAutoConfiguration.class);
      assertThat(context).doesNotHaveBean(PlatformSecurityAutoConfiguration.class);
      assertThat(context).hasSingleBean(PermitAllWebSecurityConfig.class);
      assertThat(context.getEnvironment().getProperty("platform.security.enabled")).isEqualTo("false");
    });
  }

}