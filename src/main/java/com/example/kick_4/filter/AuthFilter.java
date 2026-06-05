package com.example.kick_4.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter {

  private static final String SESSION_USER = "user";

  private static final Set<String> PUBLIC_PATHS = Set.of(
          "/pages/auth/login.jsp",
          "/pages/auth/register.jsp",
          "/pages/error/400.jsp",
          "/pages/error/401.jsp",
          "/pages/error/403.jsp",
          "/pages/error/404.jsp",
          "/pages/error/500.jsp",
          "/pages/error/503.jsp"
  );

  private static final Set<String> PUBLIC_COMMANDS = Set.of(
          "login",
          "register"
  );

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String servletPath = req.getServletPath();
    String command = req.getParameter("command");

    if (PUBLIC_PATHS.contains(servletPath)) {
      chain.doFilter(request, response);
      return;
    }

    if ("/controller".equals(servletPath) && command != null && PUBLIC_COMMANDS.contains(command.toLowerCase())) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = req.getSession(false);
    boolean authenticated = session != null && session.getAttribute(SESSION_USER) != null;

    if (authenticated) {
      chain.doFilter(request, response);
    } else {
      resp.sendRedirect(req.getContextPath() + "/pages/auth/login.jsp");
    }
  }
}