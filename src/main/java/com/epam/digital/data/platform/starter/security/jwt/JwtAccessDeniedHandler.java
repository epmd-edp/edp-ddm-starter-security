package com.epam.digital.data.platform.starter.security.jwt;

import com.epam.digital.data.platform.starter.security.dto.ErrorDto;
import com.epam.digital.data.platform.starter.security.dto.ErrorRestResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  @SuppressWarnings("findsecbugs:XSS_SERVLET")
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    // This is invoked when user tries to access a secured REST resource without the necessary authorization
    // We just send a 403 Forbidden response because there is no 'error' page to redirect to
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.getWriter().write(objectMapper.writeValueAsString(
        new ErrorRestResponseDto(
            ErrorDto.builder()
                .code("403")
                .message(accessDeniedException.getMessage())
                .traceId(MDC.get("X-B3-TraceId"))
                .build()
        )
    ));
  }
}
