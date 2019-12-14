package com.kostka.efhomework.service.management.permission;

/**
 * think about generic solution for this service too.
 * Group + User permission services almost the same
 */

public interface ManageGroupPermissionService {
    void grantPermissionToGroup(String permission, String groupName);
    void revokePermissionToGroup(String permission, String groupName);
}
