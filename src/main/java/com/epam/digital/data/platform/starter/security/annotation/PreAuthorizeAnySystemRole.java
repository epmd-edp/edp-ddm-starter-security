package com.epam.digital.data.platform.starter.security.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.access.prepost.PreAuthorize;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority(T(com.epam.digital.data.platform.starter.security.SystemRole).getRoleNames())")
public @interface PreAuthorizeAnySystemRole {

}
