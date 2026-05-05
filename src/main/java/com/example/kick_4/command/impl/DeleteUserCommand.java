package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.UserDaoImpl;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("userId");
    if (idStr == null) {
      request.setAttribute("errorMsg", "User ID is required");
      return "pages/users.jsp";
    }

    try {
      Long id = Long.parseLong(idStr);
      User user = new User();
      user.setId(id);
      boolean deleted = UserDaoImpl.getInstance().delete(user);
      if (deleted) {
        request.setAttribute("successMsg", "User deleted successfully");
      } else {
        request.setAttribute("errorMsg", "User not found");
      }
      return new ShowAllUsersCommand().execute(request);
    } catch (NumberFormatException | DaoException e) {
      throw new CommandException("Failed to delete user", e);
    }
  }
}