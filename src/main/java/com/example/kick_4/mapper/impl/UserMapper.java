package com.example.kick_4.mapper.impl;

import com.example.kick_4.entity.Role;
import com.example.kick_4.entity.User;
import com.example.kick_4.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {
  private static final String COL_ID = "id";
  private static final String COL_LOGIN = "login";
  private static final String COL_PASSWORD = "password";
  private static final String COL_ROLE = "role";

  @Override
  public User map(ResultSet resultSet) throws SQLException {
    User user = new User();
    user.setId(resultSet.getLong(COL_ID));
    user.setLogin(resultSet.getString(COL_LOGIN));
    user.setPassword(resultSet.getString(COL_PASSWORD));

    String roleStr = resultSet.getString(COL_ROLE);
    user.setRole(roleStr != null ? Role.valueOf(roleStr) : Role.USER);

    return user;
  }
}