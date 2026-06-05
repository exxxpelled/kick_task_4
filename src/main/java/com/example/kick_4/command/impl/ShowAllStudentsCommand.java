package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.command.Router;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class ShowAllStudentsCommand implements Command {

  private static final String ATTR_STUDENTS = "students";

  private static final String PAGE_STUDENTS = "pages/student/students.jsp";

  private static final String ERR_FETCH = "Failed to retrieve students";

  @Override
  public Router execute(HttpServletRequest request) throws CommandException {
    try {
      List<Student> students = StudentServiceImpl.getInstance().findAll();
      request.setAttribute(ATTR_STUDENTS, students);
      return new Router(PAGE_STUDENTS);
    } catch (ServiceException e) {
      throw new CommandException(ERR_FETCH, e);
    }
  }
}