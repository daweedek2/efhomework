package com.kostka.efhomework.service.management.assignment;

/**
 * TODO: think about generic solution for this service
 * GroupAssignment + UserAssignment services are quite similar
 */

public interface GroupAssignmentService {
    void assignParentGroupToGroup(String parentGroupName, String groupName);
    void deAssignParentGroupFromGroup(String parentGroupName, String groupName);
}
