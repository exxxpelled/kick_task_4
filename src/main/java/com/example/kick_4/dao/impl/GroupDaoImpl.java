package com.example.kick_4.dao.impl;

import com.example.kick_4.dao.BaseDao;
import com.example.kick_4.dao.GroupDao;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements BaseDao<Group>, GroupDao {
  private static final Logger logger = LogManager.getLogger(GroupDaoImpl.class);
  private static final GroupDaoImpl INSTANCE = new GroupDaoImpl();

  private static final String GROUP_ID_COLUMN = "id";
  private static final String GROUP_NAME_COLUMN = "name";

  private static final String SQL_INSERT_GROUP = """
            INSERT INTO groups (%s)
            VALUES (?)
            """.formatted(GROUP_NAME_COLUMN);

  private static final String SQL_DELETE_GROUP = """
            DELETE
            FROM groups
            WHERE %s = ?
            """.formatted(GROUP_ID_COLUMN);

  private static final String SQL_SELECT_ALL_GROUPS = """
            SELECT %s, %s
            FROM groups
            """.formatted(GROUP_ID_COLUMN, GROUP_NAME_COLUMN);

  private static final String SQL_UPDATE_GROUP = """
            UPDATE groups
            SET %s = ?
            WHERE %s = ?
            """.formatted(GROUP_NAME_COLUMN, GROUP_ID_COLUMN);

  private GroupDaoImpl() {}

  public static GroupDaoImpl getInstance() {
    return INSTANCE;
  }

  private Group mapGroup(ResultSet rs) throws SQLException {
    Group group = new Group();
    group.setId(rs.getLong(GROUP_ID_COLUMN));
    group.setName(rs.getString(GROUP_NAME_COLUMN));
    return group;
  }

  @Override
  public void insert(Group group) throws DaoException {
    if (group == null) {
      throw new DaoException("Group cannot be null");
    }
    logger.debug("Creating group with name: {}", group.getName());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_INSERT_GROUP, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, group.getName());
      int affected = ps.executeUpdate();
      if (affected == 0) {
        throw new DaoException("Creating group failed, no rows affected");
      }
      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          group.setId(keys.getLong(1));
          logger.debug("Group created with id: {}", group.getId());
        }
      }
    } catch (SQLException e) {
      logger.error("Error creating group: {}", group.getName(), e);
      throw new DaoException("Database error while creating group", e);
    }
  }

  @Override
  public boolean delete(Group group) throws DaoException {
    if (group == null || group.getId() == null) {
      logger.warn("Cannot delete group with null id");
      return false;
    }
    logger.debug("Deleting group with id: {}", group.getId());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_DELETE_GROUP)) {
      ps.setLong(1, group.getId());
      int affected = ps.executeUpdate();
      return affected > 0;
    } catch (SQLException e) {
      logger.error("Error deleting group id: {}", group.getId(), e);
      throw new DaoException("Database error while deleting group", e);
    }
  }

  @Override
  public List<Group> findAll() throws DaoException {
    logger.debug("Fetching all groups");
    List<Group> groups = new ArrayList<>();
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL_GROUPS);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        groups.add(mapGroup(rs));
      }
      logger.debug("Fetched {} groups", groups.size());
      return groups;
    } catch (SQLException e) {
      logger.error("Error fetching all groups", e);
      throw new DaoException("Database error while fetching groups", e);
    }
  }

  @Override
  public Group update(Group group) throws DaoException {
    if (group == null || group.getId() == null) {
      throw new DaoException("Cannot update group with null id");
    }
    logger.debug("Updating group id: {}", group.getId());
    try (Connection conn = ConnectionPool.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_GROUP)) {
      ps.setString(1, group.getName());
      ps.setLong(2, group.getId());
      int affected = ps.executeUpdate();
      if (affected == 0) {
        throw new DaoException("Update failed – group not found");
      }
      logger.debug("Group updated successfully");
      return group;
    } catch (SQLException e) {
      logger.error("Error updating group id: {}", group.getId(), e);
      throw new DaoException("Database error while updating group", e);
    }
  }
}