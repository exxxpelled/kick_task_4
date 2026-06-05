package com.example.kick_4.mapper.impl;

import com.example.kick_4.entity.Student;
import com.example.kick_4.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements Mapper<Student> {

  private static final String COL_ID = "id";
  private static final String COL_NAME = "name";
  private static final String COL_SURNAME = "surname";
  private static final String COL_GROUP_ID = "group_id";

  @Override
  public Student map(ResultSet resultSet) throws SQLException {
    Student student = new Student();
    student.setId(resultSet.getLong(COL_ID));
    student.setName(resultSet.getString(COL_NAME));
    student.setSurname(resultSet.getString(COL_SURNAME));
    student.setGroupNumber(resultSet.getInt(COL_GROUP_ID));
    return student;
  }
}