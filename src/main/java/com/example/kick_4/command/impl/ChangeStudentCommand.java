package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.StudentDaoImpl;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class ChangeStudentCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("studentId");
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String groupIdStr = request.getParameter("groupId");

    if (idStr == null || name == null || surname == null || groupIdStr == null) {
      request.setAttribute("errorMsg", "All fields are required");
      return "pages/editStudent.jsp";
    }

    try {
      Long id = Long.parseLong(idStr);
      int groupId = Integer.parseInt(groupIdStr);
      Student student = new Student();
      student.setId(id);
      student.setName(name);
      student.setSurname(surname);
      student.setGroupId(groupId);
      StudentDaoImpl.getInstance().update(student);
      request.setAttribute("successMsg", "Student updated successfully");
      return new ShowAllStudentsCommand().execute(request);
    } catch (NumberFormatException | DaoException e) {
      throw new CommandException("Failed to update student", e);
    }
  }
}