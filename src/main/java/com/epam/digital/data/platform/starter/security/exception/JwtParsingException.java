package com.epam.digital.data.platform.starter.security.exception;

public class JwtParsingException extends RuntimeException {

  public JwtParsingException(String message) {
    super(message);
  }
}
