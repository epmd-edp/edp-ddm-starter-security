package com.epam.digital.data.platform.starter.security.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("platform.security")
public class SecurityProperties {

  private List<String> whitelist;
}
