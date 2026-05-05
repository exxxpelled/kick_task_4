package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.UserDaoImpl;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class AddUserCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String login = request.getParameter("login");
    String password = request.getParameter("password");

    if (login == null || login.isBlank() || password == null || password.isBlank()) {
      request.setAttribute("errorMsg", "Login and password are required");
      return "pages/addUser.jsp";
    }

    User user = new User();
    user.setLogin(login);
    user.setPassword(password);

    try {
      UserDaoImpl.getInstance().insert(user);
      request.setAttribute("successMsg", "User '" + login + "' added successfully");
      return new ShowAllUsersCommand().execute(request);
    } catch (DaoException e) {
      throw new CommandException("Failed to add user", e);
    }
  }
}