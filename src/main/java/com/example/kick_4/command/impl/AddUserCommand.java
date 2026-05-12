package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserCommand implements Command {
  private static final Logger logger = LogManager.getLogger(AddUserCommand.class);
  private static final UserServiceImpl userService = UserServiceImpl.getInstance();

  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String login = request.getParameter("login");
    String password = request.getParameter("password");

    User user = new User();
    user.setLogin(login != null ? login.strip() : null);
    user.setPassword(password);

    try {
      boolean success = userService.insert(user);
      if (success) {
        request.setAttribute("successMsg", "User '" + login + "' added successfully");
        return "redirect:controller?command=SHOW_ALL_USERS";
      } else {
        request.setAttribute("errorMsg", "Failed to create user");
        return "pages/user/addUser.jsp";
      }
    } catch (ServiceException e) {
      logger.error("Error creating user", e);
      request.setAttribute("errorMsg", "Error: " + e.getMessage());
      return "pages/user/addUser.jsp";
    }
  }
}