package com.example.kick_4.command.impl;

import com.example.kick_4.command.Command;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.CommandException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class DeleteStudentCommand implements Command {
  @Override
  public String execute(HttpServletRequest request) throws CommandException {
    String idStr = request.getParameter("studentId");

    try {
      Long id = Long.parseLong(idStr);
      Student student = new Student();
      student.setId(id);
      boolean deleted = StudentServiceImpl.getInstance().delete(student);
      if (deleted) {
        request.setAttribute("successMsg", "Student deleted successfully");
      } else {
        request.setAttribute("errorMsg", "Student not found or already deleted");
      }
      return new ShowAllStudentsCommand().execute(request);
    } catch (NumberFormatException e) {
      request.setAttribute("errorMsg", "Invalid student ID format");
      return "pages/student/students.jsp";
    } catch (ServiceException | CommandException e) {
      throw new CommandException("Failed to delete student", e);
    }
  }
}