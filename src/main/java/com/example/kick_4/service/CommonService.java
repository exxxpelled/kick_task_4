package com.example.kick_4.service;

import com.example.kick_4.entity.AbstractEntity;
import com.example.kick_4.exception.ServiceException;

import java.util.List;

public interface CommonService<T extends AbstractEntity> {
  boolean insert(T t) throws ServiceException;
  boolean delete(T t) throws ServiceException;
  T update(T t) throws ServiceException;
  List<T> findAll() throws ServiceException;
}
