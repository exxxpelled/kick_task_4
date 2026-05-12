package com.example.kick_4.service.impl;

import com.example.kick_4.dao.impl.StudentDaoImpl;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.CommonService;
import com.example.kick_4.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StudentServiceImpl implements CommonService<Student>, StudentService {
  private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);
  private static final StudentServiceImpl INSTANCE = new StudentServiceImpl();
  private static final StudentDaoImpl studentDao = StudentDaoImpl.getInstance();

  private StudentServiceImpl() {}

  public static StudentServiceImpl getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean insert(Student student) throws ServiceException {
    if (student == null) throw new ServiceException("Student object cannot be null");
    if (student.getName() == null || student.getName().isBlank()) {
      throw new ServiceException("Student name cannot be empty");
    }
    if (student.getSurname() == null || student.getSurname().isBlank()) {
      throw new ServiceException("Student surname cannot be empty");
    }
    if (student.getGroupId() <= 0) {
      throw new ServiceException("Group ID must be positive");
    }
    try {
      return studentDao.insert(student);
    } catch (DaoException e) {
      logger.error("Failed to insert student: {} {}", student.getName(), student.getSurname(), e);
      throw new ServiceException("Cannot create student", e);
    }
  }

  @Override
  public boolean delete(Student student) throws ServiceException {
    if (student == null || student.getId() == null || student.getId() <= 0) {
      throw new ServiceException("Invalid student ID for deletion");
    }
    try {
      return studentDao.delete(student);
    } catch (DaoException e) {
      logger.error("Failed to delete student with id: {}", student.getId(), e);
      throw new ServiceException("Cannot delete student", e);
    }
  }

  @Override
  public Student update(Student student) throws ServiceException {
    if (student == null || student.getId() == null || student.getId() <= 0) {
      throw new ServiceException("Invalid student ID for update");
    }
    if (student.getName() == null || student.getName().isBlank()) {
      throw new ServiceException("Student name cannot be empty");
    }
    if (student.getSurname() == null || student.getSurname().isBlank()) {
      throw new ServiceException("Student surname cannot be empty");
    }
    if (student.getGroupId() <= 0) {
      throw new ServiceException("Group ID must be positive");
    }
    try {
      return studentDao.update(student);
    } catch (DaoException e) {
      logger.error("Failed to update student with id: {}", student.getId(), e);
      throw new ServiceException("Cannot update student", e);
    }
  }

  @Override
  public List<Student> findAll() throws ServiceException {
    try {
      return studentDao.findAll();
    } catch (DaoException e) {
      logger.error("Failed to fetch students", e);
      throw new ServiceException("Cannot fetch students", e);
    }
  }
}