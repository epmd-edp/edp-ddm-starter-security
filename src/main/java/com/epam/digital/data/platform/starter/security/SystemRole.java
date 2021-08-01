package com.epam.digital.data.platform.starter.security;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemRole {

  OFFICER("officer"), CITIZEN("citizen");

  private final String name;

  public static String[] getRoleNames() {
    return Stream.of(values()).map(SystemRole::getName).toArray(String[]::new);
  }
}
