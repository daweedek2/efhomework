package com.kostka.efhomework.service.entity;

import com.kostka.efhomework.entity.Group;

public interface GroupService {
    Group createGroup(String name);
    Group getGroup(String name);
    Group saveGroup(Group group);
    void deleteGroup(String name);
    boolean isGroupInDb(String name);
}
