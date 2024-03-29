package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.User;

public interface UserService {
    User createUser(String name);
    User getUser(String name);
    User saveUser(User user);
    void deleteUser(String name);
    boolean isUserInDb(String name);
}
