package com.epam.digital.data.platform.starter.security.dto.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of keycloak platform roles
 */
@AllArgsConstructor
@Getter
public enum KeycloakPlatformRole {

  OFFLINE_ACCESS("offline_access"),       // default keycloak role, will be removed
  UMA_AUTHORIZATION("uma_authorization"), // default keycloak role, will be removed
  CITIZEN("citizen"),
  ENTREPRENEUR("entrepreneur"),
  INDIVIDUAL("individual"),
  LEGAL("legal"),
  UNREGISTERED_ENTREPRENEUR("unregistered-entrepreneur"),
  UNREGISTERED_INDIVIDUAL("unregistered-individual"),
  UNREGISTERED_LEGAL("unregistered-legal");

  private final String name;

  public static boolean containsRole(String roleName) {
    return Arrays.stream(values()).anyMatch(role -> role.getName().equals(roleName));
  }
}
