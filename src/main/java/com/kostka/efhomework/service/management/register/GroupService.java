package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.Group;

/**
 * maybe also some parent EntityService -> then only overriding or extending it could be enough
 */
public interface GroupService {
    Group createGroup(String name);
    Group getGroup(String name);
    Group saveGroup(Group group);
    void deleteGroup(String name);
    boolean isGroupInDb(String name);
}
