package com.kostka.efhomework.service.management.assignment.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.service.management.assignment.GroupAssignmentService;
import com.kostka.efhomework.service.management.register.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GroupAssignmentServiceImpl implements GroupAssignmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupAssignmentServiceImpl.class);
    private GroupService groupService;

    @Autowired
    public GroupAssignmentServiceImpl(final GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public void assignParentGroupToGroup(final String parentGroupName, final String groupName) {
        final Group parentGroup = groupService.getGroup(parentGroupName);
        final Group group = groupService.getGroup(groupName);
        final Set<Group> groupSet = group.getParentGroups();
        groupSet.add(parentGroup);
        groupService.saveGroup(group);
        LOGGER.info("Parent group '{}' assigned to the group '{}'.", parentGroupName, groupName);
    }

    @Override
    public void deAssignParentGroupFromGroup(final String parentGroupName, final String groupName) {
        final Group group = groupService.getGroup(groupName);
        final Set<Group> groupSet = group.getParentGroups();
        groupSet.remove(groupService.getGroup(parentGroupName));
        groupService.saveGroup(group);
        LOGGER.info("Parent group '{}' de-assigned from the group '{}'.", parentGroupName, groupName);
    }
}
