package com.example.kick_4.dao.impl;

import com.example.kick_4.dao.BaseDao;
import com.example.kick_4.dao.UserDao;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.DaoException;
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

public class UserDaoImpl implements BaseDao<User>, UserDao {
  private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

  private static final UserDaoImpl INSTANCE = new UserDaoImpl();

  private static final String USER_ID_COLUMN = "id";
  private static final String USER_LOGIN_COLUMN = "login";
  private static final String USER_PASSWORD_COLUMN = "password";

  private static final String SQL_INSERT_USER = """
            INSERT INTO users (%s, %s)
            VALUES (?, ?)
            """.formatted(USER_LOGIN_COLUMN, USER_PASSWORD_COLUMN);

  private static final String SQL_DELETE_USER = """
            DELETE FROM users 
            WHERE %s = ?
            """.formatted(USER_ID_COLUMN);

  private static final String SQL_SELECT_ALL_USERS = """
            SELECT %s, %s, %s
            FROM users
            """.formatted(USER_ID_COLUMN, USER_LOGIN_COLUMN, USER_PASSWORD_COLUMN);

  private static final String SQL_UPDATE_USER = """
            UPDATE users
            SET %s = ?, %s = ? 
            WHERE %s = ?
            """.formatted(USER_LOGIN_COLUMN, USER_PASSWORD_COLUMN, USER_ID_COLUMN);

  private static final String SQL_SELECT_PASSWORD_WHERE_LOGIN = """
            SELECT %s
            FROM users 
            WHERE %s = ?
            """.formatted(USER_PASSWORD_COLUMN, USER_LOGIN_COLUMN);

  private UserDaoImpl() {}

  public static UserDaoImpl getInstance() {
    return INSTANCE;
  }

  private User mapUser(ResultSet rs) throws SQLException {
    User user = new User();
    user.setId(rs.getLong(USER_ID_COLUMN));
    user.setLogin(rs.getString(USER_LOGIN_COLUMN));
    user.setPassword(rs.getString(USER_PASSWORD_COLUMN));
    return user;
  }

  @Override
  public void insert(User user) throws DaoException {
    if (user == null) {
      logger.error("User cannot be null");
      throw new DaoException("User cannot be null");
    }
    logger.debug("Creating user with login: {}", user.getLogin());

    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

      preparedStatement.setString(1, user.getLogin());
      preparedStatement.setString(2, user.getPassword());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        throw new DaoException("Creating user failed, no rows affected.");
      }

      try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          long generatedId = generatedKeys.getLong(1);
          user.setId(generatedId);
          logger.debug("User created with generated id: {}", generatedId);
        } else {
          logger.warn("No generated ID was returned for user: {}", user.getLogin());
        }
      }
    } catch (SQLException e) {
      logger.error("Error creating user: {}", user.getLogin(), e);
      throw new DaoException("Database error while creating user", e);
    }
  }

  @Override
  public boolean delete(User user) throws DaoException {
    if (user == null || user.getId() == null) {
      logger.warn("Cannot delete user with null id");
      return false;
    }
    logger.debug("Deleting user with id: {}", user.getId());

    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {

      preparedStatement.setLong(1, user.getId());
      int affectedRows = preparedStatement.executeUpdate();
      boolean deleted = affectedRows > 0;

      logger.debug("User deletion completed, affected rows: {}", affectedRows);
      return deleted;
    } catch (SQLException e) {
      logger.error("Error deleting user with id: {}", user.getId(), e);
      throw new DaoException("Database error while deleting user", e);
    }
  }

  @Override
  public List<User> findAll() throws DaoException {
    logger.debug("Fetching all users");
    List<User> users = new ArrayList<>();

    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS);
         ResultSet resultSet = preparedStatement.executeQuery()) {

      while (resultSet.next()) {
        users.add(mapUser(resultSet));
      }
      logger.debug("Fetched {} users", users.size());
      return users;
    } catch (SQLException e) {
      logger.error("Error fetching all users", e);
      throw new DaoException("Database error while fetching users", e);
    }
  }

  @Override
  public User update(User user) throws DaoException {
    if (user == null || user.getId() == null) {
      throw new DaoException("Cannot update user with null id");
    }
    logger.debug("Updating user with id: {}", user.getId());

    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {

      preparedStatement.setString(1, user.getLogin());
      preparedStatement.setString(2, user.getPassword());
      preparedStatement.setLong(3, user.getId());

      int affectedRows = preparedStatement.executeUpdate();
      if (affectedRows == 0) {
        logger.warn("No user found with id {} for update", user.getId());
        throw new DaoException("Update failed – user not found");
      }
      logger.debug("User updated successfully, affected rows: {}", affectedRows);
      return user;
    } catch (SQLException e) {
      logger.error("Error updating user with id: {}", user.getId(), e);
      throw new DaoException("Database error while updating user", e);
    }
  }

  @Override
  public boolean authenticate(String login, String password) throws DaoException {
    if (login == null || password == null) {
      logger.warn("Authentication attempt with null credentials");
      return false;
    }
    logger.debug("Authenticating user: {}", login);

    try (Connection connection = ConnectionPool.getInstance().getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PASSWORD_WHERE_LOGIN)) {

      preparedStatement.setString(1, login);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          String dbPassword = resultSet.getString(USER_PASSWORD_COLUMN);

          boolean match = password.equals(dbPassword);
          logger.debug("Authentication {} for user: {}", match ? "successful" : "failed", login);
          return match;
        } else {
          logger.debug("User not found: {}", login);
          return false;
        }
      }
    } catch (SQLException e) {
      logger.error("Database error during authentication for user: {}", login, e);
      throw new DaoException("Authentication error", e);
    }
  }
}