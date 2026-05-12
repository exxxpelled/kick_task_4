package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserCommand implements Command {
  private static final Logger logger = LogManager.getLogger(DeleteUserCommand.class);
  private static final UserServiceImpl userService = UserServiceImpl.getInstance();

  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("userId");

    try {
      Long id = Long.parseLong(idStr);
      User user = new User();
      user.setId(id);
      boolean deleted = userService.delete(user);
      if (deleted) {
        request.setAttribute("successMsg", "User deleted successfully");
      } else {
        request.setAttribute("errorMsg", "User not found or already deleted");
      }
      return "pages/user/users.jsp";
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid User ID format");
      return "pages/user/users.jsp";
    } catch (ServiceException e) {
      logger.error("Error deleting user", e);
      request.setAttribute("errorMsg", "Error: " + e.getMessage());
      return "pages/user/users.jsp";
    }
  }
}