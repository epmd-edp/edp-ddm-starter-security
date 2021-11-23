/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.epam.digital.data.platform.starter.security;

import com.epam.digital.data.platform.starter.security.config.Whitelist;
import com.epam.digital.data.platform.starter.security.jwt.JwtAccessDeniedHandler;
import com.epam.digital.data.platform.starter.security.jwt.JwtConfigurer;
import com.epam.digital.data.platform.starter.security.jwt.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Order(WebSecurityConfig.DEFAULT_ORDER)
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  public static final int DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 10;

  private final JwtConfigurer securityConfigurerAdapter;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final RestAuthenticationEntryPoint authenticationErrorHandler;
  private final Whitelist whitelist;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationErrorHandler)
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .apply(securityConfigurerAdapter);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().requestMatchers(whitelist.getRequestMatcher());
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    return new InMemoryUserDetailsManager();
  }
}
