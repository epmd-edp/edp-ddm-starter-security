package com.epam.digital.data.platform.starter.security.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * Dto that represents roles list
 */
@Data
public class RolesDto implements Serializable {

  private List<String> roles;
}
