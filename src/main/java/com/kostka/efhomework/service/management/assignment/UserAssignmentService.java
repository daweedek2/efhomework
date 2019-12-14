package com.kostka.efhomework.service.management.assignment;

/**
 * think about generic solution for this service.
 * GroupAssignment + UserAssignment services are quite similar
 */

public interface UserAssignmentService {
    void assignUserToGroup(String userName, String groupName);
    void deAssignUserFromGroup(String userName, String groupName);
}
