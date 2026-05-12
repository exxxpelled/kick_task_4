package com.example.kick_4.dao.impl;

import com.example.kick_4.dao.BaseDao;
import com.example.kick_4.dao.StudentDao;
import com.example.kick_4.entity.Student;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.mapper.impl.StudentMapper;
import com.example.kick_4.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements BaseDao<Student>, StudentDao {
  private static final Logger logger = LogManager.getLogger(StudentDaoImpl.class);
  private static final StudentDaoImpl INSTANCE = new StudentDaoImpl();
  private static final StudentMapper MAPPER = new StudentMapper();

  private static final String STUDENT_ID_COLUMN = "id";
  private static final String STUDENT_NAME_COLUMN = "name";
  private static final String STUDENT_SURNAME_COLUMN = "surname";
  private static final String STUDENT_GROUP_ID_COLUMN = "groupId";

  private static final String SQL_INSERT_STUDENT = """
          INSERT INTO students (%s, %s, %s)
          VALUES (?, ?, ?)
          """.formatted(STUDENT_NAME_COLUMN, STUDENT_SURNAME_COLUMN, STUDENT_GROUP_ID_COLUMN);

  private static final String SQL_DELETE_STUDENT_BY_ID = """
          DELETE FROM students
          WHERE %s = ?
          """.formatted(STUDENT_ID_COLUMN);

  private static final String SQL_SELECT_ALL_STUDENTS = """
          SELECT %s, %s, %s, %s
          FROM students
          """.formatted(STUDENT_ID_COLUMN, STUDENT_NAME_COLUMN, STUDENT_SURNAME_COLUMN, STUDENT_GROUP_ID_COLUMN);

  private static final String SQL_UPDATE_STUDENT_BY_ID = """
          UPDATE students
          SET %s = ?, %s = ?, %s = ?
          WHERE %s = ?
          """.formatted(STUDENT_NAME_COLUMN, STUDENT_SURNAME_COLUMN, STUDENT_GROUP_ID_COLUMN, STUDENT_ID_COLUMN);

  public static StudentDaoImpl getInstance() {
    return INSTANCE;
  }

  private StudentDaoImpl() {
  }

  @Override
  public boolean insert(Student student) throws DaoException {
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, student.getName());
      preparedStatement.setString(2, student.getSurname());
      preparedStatement.setInt(3, student.getGroupId());

      int affectedRows = preparedStatement.executeUpdate();
      boolean inserted = affectedRows > 0;

      if (inserted) {
        try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
          if (keys.next()) {
            student.setId(keys.getLong(1));
            logger.debug("Student created with id: {}", student.getId());
          }
        }
      }

      return inserted;
    } catch (SQLException e) {
      logger.error("Error creating student: {} {}. {}", student.getName(), student.getSurname(), e);
      throw new DaoException("Database error while creating student", e);
    }
  }

  @Override
  public boolean delete(Student student) throws DaoException {
    logger.debug("Deleting student with id: {}", student.getId());
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_STUDENT_BY_ID)) {

      preparedStatement.setLong(1, student.getId());
      int affectedRows = preparedStatement.executeUpdate();
      boolean deleted = affectedRows > 0;

      logger.debug("Student deletion completed, affected rows: {}", affectedRows);
      return deleted;
    } catch (SQLException e) {
      logger.error("Error deleting student with id: {}", student.getId(), e);
      throw new DaoException("Database error while deleting student", e);
    }
  }

  @Override
  public Student update(Student student) throws DaoException {
    logger.debug("Updating student with id: {}", student.getId());
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STUDENT_BY_ID)) {

      preparedStatement.setString(1, student.getName());
      preparedStatement.setString(2, student.getSurname());
      preparedStatement.setInt(3, student.getGroupId());
      preparedStatement.setLong(4, student.getId());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        logger.warn("No student found with id {} for update", student.getId());
        throw new DaoException("Updating student failed, no rows affected or student not found.");
      }
      logger.debug("Student with id {} updated successfully", student.getId());
      return student;
    } catch (SQLException e) {
      logger.error("Error updating student with id: {}", student.getId(), e);
      throw new DaoException("Database error while updating student", e);
    }
  }

  @Override
  public List<Student> findAll() throws DaoException {
    logger.debug("Fetching all students");
    List<Student> students = new ArrayList<>();
    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_STUDENTS);
         ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        students.add(MAPPER.map(resultSet));
      }
      logger.debug("Fetched {} users", students.size());
      return students;
    } catch (SQLException e) {
      logger.error("Error fetching all students", e);
      throw new DaoException("Database error while fetching students", e);
    }
  }
}