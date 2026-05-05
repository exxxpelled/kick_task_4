package com.example.kick_4.dao;

import com.example.kick_4.entity.AbstractEntity;
import com.example.kick_4.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends AbstractEntity> {
  void insert(T t) throws DaoException;
  boolean delete(T t) throws DaoException;
  List<T> findAll() throws DaoException;
  T update(T t) throws DaoException;
}
