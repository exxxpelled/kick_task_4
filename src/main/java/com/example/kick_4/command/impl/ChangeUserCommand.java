package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeUserCommand implements Command {
  private static final Logger logger = LogManager.getLogger(ChangeUserCommand.class);
  private static final UserServiceImpl userService = UserServiceImpl.getInstance();

  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("userId");
    String login = request.getParameter("login");
    String password = request.getParameter("password");

    try {
      Long id = Long.parseLong(idStr);
      User user = new User();
      user.setId(id);
      user.setLogin(login != null ? login.strip() : null);
      user.setPassword(password);
      userService.update(user);
      request.setAttribute("successMsg", "User updated successfully");
      return "redirect:controller?command=SHOW_ALL_USERS";
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid User ID format");
      return "pages/user/editUser.jsp";
    } catch (ServiceException e) {
      logger.error("Error updating user", e);
      request.setAttribute("errorMsg", "Error: " + e.getMessage());
      return "pages/user/editUser.jsp";
    }
  }
}