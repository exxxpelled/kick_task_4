package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
  public static final String DEFAULT_PAGE = "index.jsp";

  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    return DEFAULT_PAGE;
  }
}
