package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.StudentDaoImpl;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAllStudentsCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    try {
      List<Student> students = StudentDaoImpl.getInstance().findAll();
      request.setAttribute("students", students);
      return "pages/students.jsp";
    } catch (DaoException e) {
      throw new CommandException("Failed to retrieve students", e);
    }
  }
}