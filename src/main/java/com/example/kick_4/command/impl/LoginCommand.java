package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.UserService;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    UserService userService = UserServiceImpl.getInstance();
    String page;
    HttpSession session = request.getSession();
    try {
      if (userService.authenticate(login, password)) {
        request.setAttribute("user", login);
        session.setAttribute("user_name", login);
        page = "pages/main.jsp";
      } else {
        request.setAttribute("login_msg", "Incorrect login or password");
        page = "pages/auth/login.jsp";
      }
      session.setAttribute("current_page", page);
    } catch (ServiceException e) {
      throw new CommandException(e);
    }

    return page;
  }
}