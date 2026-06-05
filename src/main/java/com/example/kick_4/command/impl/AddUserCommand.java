package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.Role;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserCommand implements Command {

  private static final Logger logger = LogManager.getLogger(AddUserCommand.class);

  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";
  private static final String PARAM_ROLE = "role";

  private static final String ATTR_SUCCESS_MSG = "successMsg";
  private static final String ATTR_ERROR_MSG = "errorMsg";

  private static final String PAGE_ADD_USER = "pages/user/addUser.jsp";

  private static final String MSG_SUCCESS = "User '%s' created successfully";
  private static final String MSG_FAILED = "Failed to create user";

  private final UserServiceImpl userService = UserServiceImpl.getInstance();

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String login = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);
    String roleStr = request.getParameter(PARAM_ROLE);

    User user = new User();
    user.setLogin(login != null ? login.strip() : null);
    user.setPassword(password);
    user.setRole(parseRole(roleStr));

    try {
      boolean success = userService.insert(user);
      if (success) {
        request.setAttribute(ATTR_SUCCESS_MSG, String.format(MSG_SUCCESS, login));
        return new ShowAllUsersCommand().execute(request);
      } else {
        request.setAttribute(ATTR_ERROR_MSG, MSG_FAILED);
        return new Router(PAGE_ADD_USER);
      }
    } catch (ServiceException e) {
      logger.error("Error creating user: {}", login, e);
      request.setAttribute(ATTR_ERROR_MSG, e.getMessage());
      return new Router(PAGE_ADD_USER);
    }
  }

  private Role parseRole(String roleStr) {
    if (roleStr == null) return Role.USER;
    try {
      return Role.valueOf(roleStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      return Role.USER;
    }
  }
}