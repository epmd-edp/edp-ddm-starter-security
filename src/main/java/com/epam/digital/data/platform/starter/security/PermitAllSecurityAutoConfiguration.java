package com.epam.digital.data.platform.starter.security;

import com.epam.digital.data.platform.starter.security.config.SecurityProperties;
import com.epam.digital.data.platform.starter.security.config.Whitelist;
import com.epam.digital.data.platform.starter.security.jwt.JwtConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PermitAllWebSecurityConfig.class})
@EnableConfigurationProperties(SecurityProperties.class)
@ComponentScan(basePackageClasses = {JwtConfigurer.class, Whitelist.class})
@ConditionalOnMissingBean({PlatformSecurityAutoConfiguration.class})
public class PermitAllSecurityAutoConfiguration {

}
