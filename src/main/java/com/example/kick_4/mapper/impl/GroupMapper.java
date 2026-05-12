package com.example.kick_4.mapper.impl;

import com.example.kick_4.entity.Group;
import com.example.kick_4.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements Mapper<Group> {
  private static final String GROUP_ID_COLUMN = "id";
  private static final String GROUP_NAME_COLUMN = "name";

  @Override
  public Group map(ResultSet resultSet) throws SQLException {
    Group group = new Group();
    group.setId(resultSet.getLong(GROUP_ID_COLUMN));
    group.setName(resultSet.getString(GROUP_NAME_COLUMN));
    return group;
  }
}
