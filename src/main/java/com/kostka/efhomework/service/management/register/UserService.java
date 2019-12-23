package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.User;

public interface UserService extends AbstractEntityService<User>{
    User createUser(String name);
    void deleteUser(String name);
    boolean isUserInDb(String name);
}
