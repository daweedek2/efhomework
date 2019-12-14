package com.kostka.efhomework.service.management.assignment.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.entity.User;
import com.kostka.efhomework.service.management.assignment.UserAssignmentService;
import com.kostka.efhomework.service.management.register.GroupService;
import com.kostka.efhomework.service.management.register.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAssignmentServiceImpl implements UserAssignmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAssignmentServiceImpl.class);
    private UserService userService;
    private GroupService groupService;

    public UserAssignmentServiceImpl(final UserService userService, final GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public void assignUserToGroup(final String userName, final String groupName) {
        final User user = userService.getUser(userName);
        final Group group = groupService.getGroup(groupName);
        final Set<Group> groups = user.getParentGroups();
        groups.add(group);
        userService.saveUser(user);
        LOGGER.info("User '{}' assigned to the group '{}'.", userName, groupName);
        LOGGER.info(user.toString());
        LOGGER.info(group.toString());
    }

    @Override
    public void deAssignUserFromGroup(final String userName, final String groupName) {
        final User user = userService.getUser(userName);
        final Group group = groupService.getGroup(groupName);
        final Set<Group> groups = user.getParentGroups();
        groups.remove(group);
        userService.saveUser(user);
        LOGGER.info("User '{}' de-assigned from the group '{}'.", userName, groupName);
    }
}
