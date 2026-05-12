package com.example.kick_4.mapper.impl;

import com.example.kick_4.entity.Student;
import com.example.kick_4.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements Mapper<Student> {
  private static final String STUDENT_ID_COLUMN = "id";
  private static final String STUDENT_NAME_COLUMN = "name";
  private static final String STUDENT_SURNAME_COLUMN = "surname";
  private static final String STUDENT_GROUP_ID_COLUMN = "groupId";


  @Override
  public Student map(ResultSet resultSet) throws SQLException {
    Student student = new Student();
    student.setId(resultSet.getLong(STUDENT_ID_COLUMN));
    student.setName(resultSet.getString(STUDENT_NAME_COLUMN));
    student.setSurname(resultSet.getString(STUDENT_SURNAME_COLUMN));
    student.setGroupId(resultSet.getInt(STUDENT_GROUP_ID_COLUMN));
    return student;
  }
}
