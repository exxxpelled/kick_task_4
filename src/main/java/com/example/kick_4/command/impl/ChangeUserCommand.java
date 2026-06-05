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

public class ChangeUserCommand implements Command {

  private static final Logger logger = LogManager.getLogger(ChangeUserCommand.class);

  private static final String PARAM_USER_ID = "userId";
  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";
  private static final String PARAM_ROLE = "role";

  private static final String ATTR_SUCCESS_MSG = "successMsg";
  private static final String ATTR_ERROR_MSG = "errorMsg";

  private static final String PAGE_EDIT_USER = "pages/user/editUser.jsp";

  private static final String MSG_SUCCESS = "User updated successfully";
  private static final String MSG_INVALID_ID = "Invalid User ID format";

  private final UserServiceImpl userService = UserServiceImpl.getInstance();

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter(PARAM_USER_ID);
    String login = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);
    String roleStr = request.getParameter(PARAM_ROLE);

    long id;
    try {
      id = Long.parseLong(idStr);
    } catch (NumberFormatException e) {
      request.setAttribute(ATTR_ERROR_MSG, MSG_INVALID_ID);
      return new Router(PAGE_EDIT_USER);
    }

    User user = new User();
    user.setId(id);
    user.setLogin(login != null ? login.strip() : null);
    user.setPassword(password);
    user.setRole(parseRole(roleStr));

    try {
      userService.update(user);
      request.setAttribute(ATTR_SUCCESS_MSG, MSG_SUCCESS);
      return new ShowAllUsersCommand().execute(request);
    } catch (ServiceException e) {
      logger.error("Error updating user id: {}", id, e);
      request.setAttribute(ATTR_ERROR_MSG, e.getMessage());
      return new Router(PAGE_EDIT_USER);
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