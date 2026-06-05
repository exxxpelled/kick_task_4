package com.example.kick_4.validator.impl;

import com.example.kick_4.entity.User;
import com.example.kick_4.validator.Validator;

public class UserValidator implements Validator<User> {

  private static final int MIN_LOGIN_LENGTH = 3;
  private static final int MAX_LOGIN_LENGTH = 30;
  private static final int MIN_PASSWORD_LENGTH = 4;
  private static final int MAX_PASSWORD_LENGTH = 50;
  private static final String LOGIN_PATTERN = "[a-zA-Z0-9_\\.\\-]+";

  private static final UserValidator INSTANCE = new UserValidator();

  private UserValidator() {}

  public static UserValidator getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean isValid(User user) {
    return user != null
            && isLoginValid(user.getLogin())
            && isPasswordValid(user.getPassword());
  }

  public boolean isLoginValid(String login) {
    return login != null
            && !login.isBlank()
            && login.length() >= MIN_LOGIN_LENGTH
            && login.length() <= MAX_LOGIN_LENGTH
            && login.matches(LOGIN_PATTERN);
  }

  public boolean isPasswordValid(String password) {
    return password != null
            && !password.isBlank()
            && password.length() >= MIN_PASSWORD_LENGTH
            && password.length() <= MAX_PASSWORD_LENGTH;
  }
}