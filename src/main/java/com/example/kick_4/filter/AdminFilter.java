package com.example.kick_4.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter(urlPatterns = {"/*"})
public class AdminFilter implements Filter {

  private static final String SESSION_ROLE = "user_role";
  private static final String ROLE_ADMIN = "ADMIN";

  private static final Set<String> ADMIN_COMMANDS = Set.of(
          "add_user",
          "delete_user",
          "change_user",
          "show_all_users"
  );

  private static final Set<String> ADMIN_PAGES = Set.of(
          "/pages/user/users.jsp",
          "/pages/user/addUser.jsp",
          "/pages/user/editUser.jsp"
  );

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String servletPath = req.getServletPath();
    String command = req.getParameter("command");

    boolean isAdminPage = ADMIN_PAGES.contains(servletPath);
    boolean isAdminCommand = command != null && ADMIN_COMMANDS.contains(command.toLowerCase());

    if (!isAdminPage && !isAdminCommand) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = req.getSession(false);
    String role = session != null ? (String) session.getAttribute(SESSION_ROLE) : null;

    if (ROLE_ADMIN.equals(role)) {
      chain.doFilter(request, response);
    } else {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN,
              "Access denied: administrator role required");
    }
  }
}