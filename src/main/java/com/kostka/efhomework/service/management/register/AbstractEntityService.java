package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.AbstractEntity;
import com.kostka.efhomework.entity.Group;

public interface AbstractEntityService<T extends AbstractEntity> {
    T save(T entity);
    T get(String name);
}
