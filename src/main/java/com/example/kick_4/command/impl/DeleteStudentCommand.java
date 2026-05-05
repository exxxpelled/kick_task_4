package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.StudentDaoImpl;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteStudentCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("studentId");
    if (idStr == null) {
      request.setAttribute("errorMsg", "Student ID is required");
      return "pages/students.jsp";
    }

    try {
      Long id = Long.parseLong(idStr);
      Student student = new Student();
      student.setId(id);
      boolean deleted = StudentDaoImpl.getInstance().delete(student);
      if (deleted) {
        request.setAttribute("successMsg", "Student deleted successfully");
      } else {
        request.setAttribute("errorMsg", "Student not found");
      }
      return new ShowAllStudentsCommand().execute(request);
    } catch (NumberFormatException | DaoException e) {
      throw new CommandException("Failed to delete student", e);
    }
  }
}