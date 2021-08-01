package com.epam.digital.data.platform.starter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(PermitAllWebSecurityConfig.DEFAULT_ORDER)
@Configuration
@EnableWebSecurity
public class PermitAllWebSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final int DEFAULT_ORDER = WebSecurityConfig.DEFAULT_ORDER + 1;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().permitAll();
  }
}
