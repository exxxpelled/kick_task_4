package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllUsersCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    try {
      List<User> users = UserServiceImpl.getInstance().findAll();
      request.setAttribute("users", users);
      return "pages/user/users.jsp";
    } catch (ServiceException e) {
      throw new CommandException("Failed to retrieve users", e);
    }
  }
}