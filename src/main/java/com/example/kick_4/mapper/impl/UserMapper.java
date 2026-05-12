package com.example.kick_4.mapper.impl;

import com.example.kick_4.entity.User;
import com.example.kick_4.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {
  private static final String USER_ID_COLUMN = "id";
  private static final String USER_LOGIN_COLUMN = "login";
  private static final String USER_PASSWORD_COLUMN = "password";

  @Override
  public User map(ResultSet resultSet) throws SQLException {
    User user = new User();
    user.setId(resultSet.getLong(USER_ID_COLUMN));
    user.setLogin(resultSet.getString(USER_LOGIN_COLUMN));
    user.setPassword(resultSet.getString(USER_PASSWORD_COLUMN));
    return user;
  }
}
