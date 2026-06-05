package com.example.kick_4.validator.impl;

import com.example.kick_4.entity.Student;
import com.example.kick_4.validator.Validator;

public class StudentValidator implements Validator<Student> {

  private static final int MAX_NAME_LENGTH = 50;

  private static final StudentValidator INSTANCE = new StudentValidator();

  private StudentValidator() {
  }

  public static StudentValidator getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean isValid(Student student) {
    if (student == null) {
      return false;
    }
    if (!isNameValid(student.getName())) {
      return false;
    }
    if (!isNameValid(student.getSurname())) {
      return false;
    }
    if (student.getGroupNumber() <= 0) {
      return false;
    }
    return true;
  }

  public boolean isNameValid(String name) {
    return name != null
            && !name.isBlank()
            && name.length() <= MAX_NAME_LENGTH
            && name.matches("[\\p{L}\\s\\-']+");
  }

  public boolean isGroupNumberValid(int groupNumber) {
    return groupNumber > 0;
  }
}