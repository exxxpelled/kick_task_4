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

  private static final String COL_ID = "id";
  private static final String COL_NAME = "name";
  private static final String COL_SURNAME = "surname";
  private static final String COL_GROUP_ID = "group_id";   // plain lowercase — no quotes needed

  private static final String SQL_INSERT = """
          INSERT INTO students (name, surname, group_id)
          VALUES (?, ?, ?)
          """;

  private static final String SQL_DELETE = """
          DELETE FROM students
          WHERE id = ?
          """;

  private static final String SQL_SELECT_ALL = """
          SELECT id, name, surname, group_id
          FROM students
          ORDER BY id
          """;

  private static final String SQL_UPDATE = """
          UPDATE students
          SET name = ?, surname = ?, group_id = ?
          WHERE id = ?
          """;

  private StudentDaoImpl() {
  }

  public static StudentDaoImpl getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean insert(Student student) throws DaoException {
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, student.getName());
      ps.setString(2, student.getSurname());
      ps.setInt(3, student.getGroupNumber());

      int rows = ps.executeUpdate();
      if (rows > 0) {
        try (ResultSet keys = ps.getGeneratedKeys()) {
          if (keys.next()) {
            student.setId(keys.getLong(1));
            logger.debug("Student inserted with id: {}", student.getId());
          }
        }
      }
      return rows > 0;
    } catch (SQLException e) {
      logger.error("Error inserting student: {} {}", student.getName(), student.getSurname(), e);
      throw new DaoException("Database error while creating student", e);
    }
  }

  @Override
  public boolean delete(Student student) throws DaoException {
    logger.debug("Deleting student id: {}", student.getId());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

      ps.setLong(1, student.getId());
      int rows = ps.executeUpdate();
      logger.debug("Deleted {} row(s) for student id: {}", rows, student.getId());
      return rows > 0;
    } catch (SQLException e) {
      logger.error("Error deleting student id: {}", student.getId(), e);
      throw new DaoException("Database error while deleting student", e);
    }
  }

  @Override
  public Student update(Student student) throws DaoException {
    logger.debug("Updating student id: {}", student.getId());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

      ps.setString(1, student.getName());
      ps.setString(2, student.getSurname());
      ps.setInt(3, student.getGroupNumber());
      ps.setLong(4, student.getId());

      int rows = ps.executeUpdate();
      if (rows == 0) {
        throw new DaoException("Update failed — student not found with id: " + student.getId());
      }
      logger.debug("Student id {} updated successfully", student.getId());
      return student;
    } catch (SQLException e) {
      logger.error("Error updating student id: {}", student.getId(), e);
      throw new DaoException("Database error while updating student", e);
    }
  }

  @Override
  public List<Student> findAll() throws DaoException {
    logger.debug("Fetching all students");
    List<Student> students = new ArrayList<>();
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        students.add(MAPPER.map(rs));
      }
      logger.debug("Fetched {} students", students.size());
      return students;
    } catch (SQLException e) {
      logger.error("Error fetching all students", e);
      throw new DaoException("Database error while fetching students", e);
    }
  }
}