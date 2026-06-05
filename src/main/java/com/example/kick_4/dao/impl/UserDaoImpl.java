package com.example.kick_4.dao.impl;

import com.example.kick_4.dao.BaseDao;
import com.example.kick_4.dao.UserDao;
import com.example.kick_4.entity.User;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.mapper.impl.UserMapper;
import com.example.kick_4.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements BaseDao<User>, UserDao {

  private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
  private static final UserDaoImpl INSTANCE = new UserDaoImpl();
  private static final UserMapper MAPPER = new UserMapper();

  private static final String COL_ID = "id";
  private static final String COL_LOGIN = "login";
  private static final String COL_PASSWORD = "password";
  private static final String COL_ROLE = "role";

  private static final String SQL_INSERT = """
          INSERT INTO users (login, password, role)
          VALUES (?, ?, ?::user_role)
          """;

  private static final String SQL_DELETE = """
          DELETE FROM users
          WHERE id = ?
          """;

  private static final String SQL_SELECT_ALL = """
          SELECT id, login, password, role
          FROM users
          ORDER BY id
          """;

  private static final String SQL_SELECT_BY_LOGIN = """
          SELECT id, login, password, role
          FROM users
          WHERE login = ?
          """;

  private static final String SQL_UPDATE = """
          UPDATE users
          SET login = ?, password = ?, role = ?::user_role
          WHERE id = ?
          """;

  private static final String SQL_UPDATE_WITHOUT_PASSWORD = """
          UPDATE users
          SET login = ?, role = ?::user_role
          WHERE id = ?
          """;

  private UserDaoImpl() {
  }

  public static UserDaoImpl getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean insert(User user) throws DaoException {
    logger.debug("Inserting user: {}", user.getLogin());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

      ps.setString(1, user.getLogin());
      ps.setString(2, user.getPassword());
      ps.setString(3, user.getRole().name());

      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      logger.error("Error inserting user: {}", user.getLogin(), e);
      throw new DaoException("Database error while creating user", e);
    }
  }

  @Override
  public boolean delete(User user) throws DaoException {
    logger.debug("Deleting user id: {}", user.getId());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

      ps.setLong(1, user.getId());
      int rows = ps.executeUpdate();
      logger.debug("Deleted {} row(s) for user id: {}", rows, user.getId());
      return rows > 0;
    } catch (SQLException e) {
      logger.error("Error deleting user id: {}", user.getId(), e);
      throw new DaoException("Database error while deleting user", e);
    }
  }

  @Override
  public User update(User user) throws DaoException {
    logger.debug("Updating user id: {}", user.getId());

    boolean changePassword = user.getPassword() != null && !user.getPassword().isBlank();
    String sql = changePassword ? SQL_UPDATE : SQL_UPDATE_WITHOUT_PASSWORD;

    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      if (changePassword) {
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole().name());
        ps.setLong(4, user.getId());
      } else {
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getRole().name());
        ps.setLong(3, user.getId());
      }

      int rows = ps.executeUpdate();
      if (rows == 0) {
        throw new DaoException("Update failed — user not found with id: " + user.getId());
      }
      logger.debug("User id {} updated successfully", user.getId());
      return user;
    } catch (SQLException e) {
      logger.error("Error updating user id: {}", user.getId(), e);
      throw new DaoException("Database error while updating user", e);
    }
  }

  @Override
  public List<User> findAll() throws DaoException {
    logger.debug("Fetching all users");
    List<User> users = new ArrayList<>();
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
         ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        users.add(MAPPER.map(rs));
      }
      logger.debug("Fetched {} users", users.size());
      return users;
    } catch (SQLException e) {
      logger.error("Error fetching all users", e);
      throw new DaoException("Database error while fetching users", e);
    }
  }

  @Override
  public Optional<User> findByLogin(String login) throws DaoException {
    logger.debug("Looking up user by login: {}", login);
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_LOGIN)) {

      ps.setString(1, login);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(MAPPER.map(rs));
        }
        return Optional.empty();
      }
    } catch (SQLException e) {
      logger.error("Error fetching user by login: {}", login, e);
      throw new DaoException("Database error while fetching user", e);
    }
  }

  @Override
  public boolean authenticate(String login, String password) throws DaoException {
    return findByLogin(login)
            .map(user -> user.getPassword().equals(password))
            .orElse(false);
  }
}