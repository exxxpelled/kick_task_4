package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class RegisterCommand implements Command {

  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";

  private static final String ATTR_ERROR_MSG = "errorMsg";
  private static final String ATTR_SUCCESS_MSG = "successMsg";
  private static final String ATTR_PREFILL_LOGIN = "prefillLogin";

  private static final String PAGE_REGISTER = "pages/auth/register.jsp";
  private static final String PAGE_LOGIN = "pages/auth/login.jsp";

  private static final String MSG_SUCCESS = "Registration successful. Please sign in.";

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String login = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);

    User user = new User();
    user.setLogin(login != null ? login.strip() : null);
    user.setPassword(password);

    try {
      UserServiceImpl.getInstance().register(user);
      request.setAttribute(ATTR_SUCCESS_MSG, MSG_SUCCESS);
      return new Router(PAGE_LOGIN);
    } catch (ServiceException e) {
      request.setAttribute(ATTR_ERROR_MSG, e.getMessage());
      request.setAttribute(ATTR_PREFILL_LOGIN, login);
      return new Router(PAGE_REGISTER);
    }
  }
}