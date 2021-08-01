package com.epam.digital.data.platform.starter.security.dto;

import com.epam.digital.data.platform.starter.security.dto.enums.SubjectType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keycloak.representations.IDToken;

/**
 * Dto that represents JWT claim set
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtClaimsDto extends IDToken {

  @JsonProperty("allowed-origins")
  private List<String> allowedOrigins;
  @JsonProperty("realm_access")
  private RolesDto realmAccess;
  @JsonProperty("resource_access")
  private Map<String, RolesDto> resourceAccess;
  private String scope;
  private List<String> roles;

  private String edrpou;
  private String drfo;
  private String fullName;
  private SubjectType subjectType;
  private boolean representative;
}
