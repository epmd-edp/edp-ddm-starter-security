package com.epam.digital.data.platform.starter.security;

import com.epam.digital.data.platform.starter.security.config.SecurityProperties;
import com.epam.digital.data.platform.starter.security.config.Whitelist;
import com.epam.digital.data.platform.starter.security.jwt.JwtConfigurer;
import com.epam.digital.data.platform.starter.security.jwt.RestAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebSecurityConfig.class})
@EnableConfigurationProperties(SecurityProperties.class)
@ComponentScan(basePackageClasses = {JwtConfigurer.class, Whitelist.class})
@ConditionalOnProperty(prefix = "platform.security", name = "enabled", matchIfMissing = true)
@RequiredArgsConstructor
public class PlatformSecurityAutoConfiguration {

  private final ObjectMapper objectMapper;

  @Bean
  @ConditionalOnMissingBean
  public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
    return new RestAuthenticationEntryPoint(objectMapper);
  }
}
