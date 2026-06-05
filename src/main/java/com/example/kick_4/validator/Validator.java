package com.example.kick_4.validator;

import com.example.kick_4.entity.AbstractEntity;

public interface Validator<T extends AbstractEntity> {
  boolean isValid(T t);
}
