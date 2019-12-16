package com.kostka.efhomework.service.management.permission;

/**
 * think about generic solution for this service too.
 * Group + User permission services almost the same
 */
public interface ManageUserPermissionService {
    void grantPermissionToUser(String permission, String userName);
    void revokePermissionFromUser(String permission, String userName);
}
