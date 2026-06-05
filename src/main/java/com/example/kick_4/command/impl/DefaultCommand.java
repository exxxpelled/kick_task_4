package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {

  private static final String PAGE_LOGIN = "pages/auth/login.jsp";

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    return new Router(PAGE_LOGIN);
  }
}