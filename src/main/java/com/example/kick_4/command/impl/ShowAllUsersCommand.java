package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ShowAllUsersCommand implements Command {

  private static final String ATTR_USERS = "users";

  private static final String PAGE_USERS = "pages/user/users.jsp";

  private static final String ERR_FETCH = "Failed to retrieve users";

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    try {
      List<User> users = UserServiceImpl.getInstance().findAll();
      request.setAttribute(ATTR_USERS, users);
      return new Router(PAGE_USERS);
    } catch (ServiceException e) {
      throw new CommandException(ERR_FETCH, e);
    }
  }
}