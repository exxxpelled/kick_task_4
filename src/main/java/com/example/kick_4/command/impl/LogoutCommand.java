package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    request.getSession().invalidate();
    return "/pages/auth/login.jsp";
  }
}