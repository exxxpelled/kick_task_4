package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.dao.impl.StudentDaoImpl;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.DaoException;
import jakarta.servlet.http.HttpServletRequest;

public class AddStudentCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String groupIdStr = request.getParameter("groupId");

    if (name == null || name.isBlank() || surname == null || surname.isBlank() || groupIdStr == null) {
      request.setAttribute("errorMsg", "All fields are required");
      return "pages/addStudent.jsp";
    }

    int groupId;
    try {
      groupId = Integer.parseInt(groupIdStr);
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid group ID");
      return "pages/addStudent.jsp";
    }

    Student student = new Student();
    student.setName(name);
    student.setSurname(surname);
    student.setGroupId(groupId);

    try {
      StudentDaoImpl.getInstance().insert(student);
      request.setAttribute("successMsg", "Student " + name + " " + surname + " added successfully");
      return new ShowAllStudentsCommand().execute(request);
    } catch (DaoException e) {
      throw new CommandException("Failed to add student", e);
    }
  }
}