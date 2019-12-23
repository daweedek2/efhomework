package com.kostka.efhomework.service.management.permission;

/**
 * think about generic solution for this service too.
 * Group + User permission services almost the same
 */

public interface ManagePermissionService {
    void grantPermission(String permission, String name);
    void revokePermission(String permission, String name);
}
