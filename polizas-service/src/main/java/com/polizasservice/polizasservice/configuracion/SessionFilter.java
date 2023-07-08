package com.polizasservice.polizasservice.configuracion;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Order(value = 1)
public class SessionFilter implements Filter {
  @Autowired
  private AppConfig appConfig;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    // Ahora puedes acceder a las funcionalidades específicas de HttpServletRequest
    String method = req.getMethod();
    String uri = req.getRequestURI();
    // ...

    // Continuar con el procesamiento de la solicitud
    chain.doFilter(request, response);
  }

}
