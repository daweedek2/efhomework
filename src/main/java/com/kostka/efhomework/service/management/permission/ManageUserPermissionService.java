package com.kostka.efhomework.service.management.permission;

public interface ManageUserPermissionService {
    void grantPermissionToUser(String permission, String userName);
    void revokePermissionToUser(String permission, String userName);
}
