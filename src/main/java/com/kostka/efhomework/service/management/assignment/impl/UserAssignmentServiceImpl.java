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

/**
 * TODO: This class is almost the same as UserAssignment Service -> think about generic solution.
 */
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
        final User user = userService.get(userName);
        final Group group = groupService.get(groupName);
        final Set<Group> groups = user.getParentGroups();
        groups.add(group);
        userService.save(user);
        LOGGER.info("User '{}' assigned to the group '{}'.", userName, groupName);
    }

    @Override
    public void deAssignUserFromGroup(final String userName, final String groupName) {
        final User user = userService.get(userName);
        final Group group = groupService.get(groupName);
        final Set<Group> groups = user.getParentGroups();
        groups.remove(group);
        userService.save(user);
        LOGGER.info("User '{}' de-assigned from the group '{}'.", userName, groupName);
    }
}
