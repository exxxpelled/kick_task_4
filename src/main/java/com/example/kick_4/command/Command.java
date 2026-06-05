package com.example.kick_4.command;

import com.example.kick_4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
  Router execute(HttpServletRequest request) throws CommandException;
}
