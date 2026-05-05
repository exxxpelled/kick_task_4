package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.UserDaoImpl;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeUserCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("userId");
    String login = request.getParameter("login");
    String password = request.getParameter("password");

    if (idStr == null || login == null || password == null) {
      request.setAttribute("errorMsg", "All fields are required");
      return "pages/editUser.jsp";
    }

    try {
      Long id = Long.parseLong(idStr);
      User user = new User();
      user.setId(id);
      user.setLogin(login);
      user.setPassword(password);
      UserDaoImpl.getInstance().update(user);
      request.setAttribute("successMsg", "User updated successfully");
      return new ShowAllUsersCommand().execute(request);
    } catch (NumberFormatException | DaoException e) {
      throw new CommandException("Failed to update user", e);
    }
  }
}