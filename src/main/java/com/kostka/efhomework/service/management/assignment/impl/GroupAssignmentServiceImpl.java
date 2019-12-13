package com.kostka.efhomework.service.management.assignment.impl;

import com.kostka.efhomework.entity.Group;
import com.kostka.efhomework.service.management.assignment.GroupAssignmentService;
import com.kostka.efhomework.service.management.register.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GroupAssignmentServiceImpl implements GroupAssignmentService {
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
    }

    @Override
    public void deAssignParentGroupFromGroup(final String parentGroupName, final String groupName) {
        final Group group = groupService.getGroup(groupName);
        final Set<Group> groupSet = group.getParentGroups();
        groupSet.remove(groupService.getGroup(parentGroupName));
    }
}
