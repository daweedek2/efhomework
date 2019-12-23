package com.kostka.efhomework.service.management.register;

import com.kostka.efhomework.entity.Group;

/**
 * maybe also some parent EntityService -> then only overriding or extending it could be enough
 */
public interface GroupService extends AbstractEntityService<Group>{
    // TODO tyto metoddy taky presunout do AbstractEntityService
    Group createGroup(String name);
    void deleteGroup(String name);
    boolean isGroupInDb(String name);
}
