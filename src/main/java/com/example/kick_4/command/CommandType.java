package com.example.kick_4.command;

import com.example.kick_4.command.impl.*;

public enum CommandType {
  LOGIN(new LoginCommand()),
  LOGOUT(new LogoutCommand()),

  ADD_USER(new AddUserCommand()),
  DELETE_USER(new DeleteUserCommand()),
  CHANGE_USER(new ChangeUserCommand()),
  SHOW_ALL_USERS(new ShowAllUsersCommand()),

  ADD_GROUP(new AddGroupCommand()),
  DELETE_GROUP(new DeleteGroupCommand()),
  CHANGE_GROUP(new ChangeGroupCommand()),
  SHOW_ALL_GROUPS(new ShowAllGroupsCommand()),

  ADD_STUDENT(new AddStudentCommand()),
  CHANGE_STUDENT(new ChangeStudentCommand()),
  DELETE_STUDENT(new DeleteStudentCommand()),
  SHOW_ALL_STUDENTS(new ShowAllStudentsCommand()),

  DEFAULT(new DefaultCommand());

  private Command command;

  CommandType(Command command) {
    this.command = command;
  }

  public static Command defineCommand(String commandStr) {
    try {
      CommandType current = CommandType.valueOf(commandStr.toUpperCase());
      return current.command;
    } catch (IllegalArgumentException e) {
      return DEFAULT.command;
    }
  }
}