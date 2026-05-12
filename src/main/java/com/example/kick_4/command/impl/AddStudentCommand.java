package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class AddStudentCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String groupIdStr = request.getParameter("groupId");

    Student student = new Student();
    student.setName(name);
    student.setSurname(surname);
    try {
      int groupId = Integer.parseInt(groupIdStr);
      student.setGroupId(groupId);
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid group ID");
      return "pages/student/addStudent.jsp";
    }

    try {
      StudentServiceImpl.getInstance().insert(student);
      request.setAttribute("successMsg", "Student added successfully");
      return "redirect:controller?command=SHOW_ALL_STUDENTS";
    } catch (ServiceException e) {
      throw new CommandException("Failed to add student", e);
    }
  }
}