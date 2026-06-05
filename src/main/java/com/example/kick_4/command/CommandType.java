package com.example.kick_4.command;

import com.example.kick_4.command.impl.*;

public enum CommandType {
  LOGIN(new LoginCommand()),
  LOGOUT(new LogoutCommand()),
  REGISTER(new RegisterCommand()),

  ADD_USER(new AddUserCommand()),
  DELETE_USER(new DeleteUserCommand()),
  CHANGE_USER(new ChangeUserCommand()),
  SHOW_ALL_USERS(new ShowAllUsersCommand()),

  ADD_STUDENT(new AddStudentCommand()),
  CHANGE_STUDENT(new ChangeStudentCommand()),
  DELETE_STUDENT(new DeleteStudentCommand()),
  SHOW_ALL_STUDENTS(new ShowAllStudentsCommand()),

  DEFAULT(new DefaultCommand());

  private final Command command;

  CommandType(Command command) {
    this.command = command;
  }

  public static Command defineCommand(String commandStr) {
    if (commandStr == null || commandStr.isBlank()) {
      return DEFAULT.command;
    }
    try {
      return CommandType.valueOf(commandStr.toUpperCase()).command;
    } catch (IllegalArgumentException e) {
      return DEFAULT.command;
    }
  }
}