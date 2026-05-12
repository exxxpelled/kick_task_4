package com.example.kick_4.mapper;

import com.example.kick_4.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T extends AbstractEntity> {
  T map(ResultSet resultSet) throws SQLException;
}
