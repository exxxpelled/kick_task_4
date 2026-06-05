package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {

  private static final Logger logger = LogManager.getLogger(DeleteUserCommand.class);

  private static final String PARAM_USER_ID = "userId";

  private static final String ATTR_SUCCESS_MSG = "successMsg";
  private static final String ATTR_ERROR_MSG = "errorMsg";

  private static final String PAGE_USERS = "pages/user/users.jsp";

  private static final String MSG_DELETED = "User deleted successfully";
  private static final String MSG_NOT_FOUND = "User not found or already deleted";
  private static final String MSG_INVALID_ID = "Invalid User ID format";

  private final UserServiceImpl userService = UserServiceImpl.getInstance();

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter(PARAM_USER_ID);

    long id;
    try {
      id = Long.parseLong(idStr);
    } catch (NumberFormatException e) {
      request.setAttribute(ATTR_ERROR_MSG, MSG_INVALID_ID);
      return new Router(PAGE_USERS);
    }

    User user = new User();
    user.setId(id);

    try {
      boolean deleted = userService.delete(user);
      if (deleted) {
        request.setAttribute(ATTR_SUCCESS_MSG, MSG_DELETED);
      } else {
        request.setAttribute(ATTR_ERROR_MSG, MSG_NOT_FOUND);
      }
      return new ShowAllUsersCommand().execute(request);
    } catch (ServiceException e) {
      logger.error("Error deleting user with id: {}", id, e);
      request.setAttribute(ATTR_ERROR_MSG, e.getMessage());
      return new Router(PAGE_USERS);
    }
  }
}