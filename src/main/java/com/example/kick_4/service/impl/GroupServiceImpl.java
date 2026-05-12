package com.example.kick_4.service.impl;

import com.example.kick_4.dao.impl.GroupDaoImpl;
import com.example.kick_4.entity.Group;
import com.example.kick_4.exception.DaoException;
import com.example.kick_4.exception.ServiceException;
import com.example.kick_4.service.CommonService;
import com.example.kick_4.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroupServiceImpl implements CommonService<Group>, GroupService {
  private static final Logger logger = LogManager.getLogger(GroupServiceImpl.class);
  private static final GroupServiceImpl INSTANCE = new GroupServiceImpl();
  private static final GroupDaoImpl groupDao = GroupDaoImpl.getInstance();

  private GroupServiceImpl() {}

  public static GroupServiceImpl getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean insert(Group group) throws ServiceException {
    if (group == null) throw new ServiceException("Group object cannot be null");
    if (group.getName() == null || group.getName().isBlank()) {
      throw new ServiceException("Group name cannot be empty");
    }
    try {
      return groupDao.insert(group);
    } catch (DaoException e) {
      logger.error("Failed to insert group: {}", group.getName(), e);
      throw new ServiceException("Cannot create group", e);
    }
  }

  @Override
  public boolean delete(Group group) throws ServiceException {
    if (group == null || group.getId() == null || group.getId() <= 0) {
      throw new ServiceException("Invalid group ID for deletion");
    }
    try {
      return groupDao.delete(group);
    } catch (DaoException e) {
      logger.error("Failed to delete group with id: {}", group.getId(), e);
      throw new ServiceException("Cannot delete group", e);
    }
  }

  @Override
  public Group update(Group group) throws ServiceException {
    if (group == null || group.getId() == null || group.getId() <= 0) {
      throw new ServiceException("Invalid group ID for update");
    }
    if (group.getName() == null || group.getName().isBlank()) {
      throw new ServiceException("Group name cannot be empty");
    }
    try {
      return groupDao.update(group);
    } catch (DaoException e) {
      logger.error("Failed to update group with id: {}", group.getId(), e);
      throw new ServiceException("Cannot update group", e);
    }
  }

  @Override
  public List<Group> findAll() throws ServiceException {
    try {
      return groupDao.findAll();
    } catch (DaoException e) {
      logger.error("Failed to fetch groups", e);
      throw new ServiceException("Cannot fetch groups", e);
    }
  }
}